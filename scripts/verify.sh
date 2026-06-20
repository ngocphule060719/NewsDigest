#!/usr/bin/env bash
# NewsDigest release verification — Phase 8 §9.2 boundary greps + §4.9 test matrix.
# Usage:
#   ./scripts/verify.sh              # greps + unit tests + assembleDebug
#   ./scripts/verify.sh --greps-only # boundary checks only
#   RUN_INSTRUMENTED=1 ./scripts/verify.sh  # also run connectedDebugAndroidTest (emulator)
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

GREPS_ONLY=false
if [[ "${1:-}" == "--greps-only" ]]; then
  GREPS_ONLY=true
fi

log() { printf '==> %s\n' "$*"; }
fail() { printf 'ERROR: %s\n' "$*" >&2; exit 1; }

run_boundary_greps() {
  log 'Boundary grep: app must not reference :repoImpl / :data:local / :data:remote'
  if rg ':repoImpl|:data:local|:data:remote' app/; then
    fail 'Found forbidden module references under app/'
  fi
  log '  OK (no matches)'

  log 'Boundary grep: app Kotlin must not import data-layer impl types'
  if rg 'NewsRepositoryImpl|ArticleDao|NewsApiService' app/src/main/java/; then
    fail 'Found forbidden impl types under app/src/main/java/'
  fi
  log '  OK (no matches)'

  log 'Boundary grep: single Theme { root in app presentation'
  local theme_matches
  theme_matches="$(rg -c 'Theme\s*\{' app/src/main/java/ || true)"
  local theme_count
  theme_count="$(echo "$theme_matches" | awk -F: '{s+=$2} END {print s+0}')"
  if [[ "$theme_count" -ne 1 ]]; then
    rg 'Theme\s*\{' app/src/main/java/ || true
    fail "Expected exactly 1 Theme { in app/src/main/java/, found $theme_count"
  fi
  rg 'Theme\s*\{' app/src/main/java/
  log '  OK (exactly one Theme { — NavGraph.kt)'
}

run_test_matrix() {
  log 'Gradle: :domain:test'
  ./gradlew :domain:test

  log 'Gradle: :repoImpl:testDebugUnitTest'
  ./gradlew :repoImpl:testDebugUnitTest

  log 'Gradle: :data:local:testDebugUnitTest'
  ./gradlew :data:local:testDebugUnitTest

  log 'Gradle: :data:remote:testDebugUnitTest'
  ./gradlew :data:remote:testDebugUnitTest

  log 'Gradle: :design-system:testDebugUnitTest'
  ./gradlew :design-system:testDebugUnitTest

  log 'Gradle: :app:testDebugUnitTest'
  ./gradlew :app:testDebugUnitTest

  if [[ -d appSupport ]]; then
    log 'Gradle: :appSupport:testDebugUnitTest'
    ./gradlew :appSupport:testDebugUnitTest
  fi

  if [[ "${RUN_INSTRUMENTED:-0}" == "1" ]]; then
    log 'Gradle: :app:connectedDebugAndroidTest (emulator required)'
    ./gradlew :app:connectedDebugAndroidTest
  else
    log 'Skipping :app:connectedDebugAndroidTest (set RUN_INSTRUMENTED=1 to run)'
  fi

  log 'Gradle: testDebugUnitTest (all modules)'
  ./gradlew testDebugUnitTest

  log 'Gradle: assembleDebug'
  ./gradlew assembleDebug
}

log "NewsDigest verify (root: $ROOT)"
run_boundary_greps

if [[ "$GREPS_ONLY" == true ]]; then
  log 'Done (--greps-only).'
  exit 0
fi

run_test_matrix
log 'All verify steps passed.'
