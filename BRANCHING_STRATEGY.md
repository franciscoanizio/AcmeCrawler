# Branching Strategy - AcmeCrawler Release 1.0

## Overview
This project uses **Git Flow**, a robust strategy for managing releases and continuous development.

## Branch Structure

### Main Branches

#### 1. **main** (Production)
- Production-ready code
- Only merges from `release/` and `hotfix/`
- Each commit should have a version tag (v1.0.0, v1.0.1, etc.)
- Always stable and ready for deployment

#### 2. **develop** (Development)
- Development integration branch
- Latest code with completed features
- Base for new feature branches
- Should always be in a functional state

### Support Branches

#### 3. **feature/*** (New Features)
```bash
git checkout -b feature/new-feature develop
```
- Created from `develop`
- Pattern: `feature/descriptive-name`
- Examples:
  - `feature/improve-parser`
  - `feature/add-logger`
  - `feature/refactor-crawler`
- Delete after merging into develop

#### 4. **release/1.0** (Release Preparation)
```bash
git checkout -b release/1.0 develop
```
- Created when `develop` is ready for release
- Only allows:
  - Version bumping
  - Critical bug fixes
  - Documentation adjustments
- Merges into `main` and back to `develop`
- Delete after release is complete

#### 5. **hotfix/*** (Urgent Fixes)
```bash
git checkout -b hotfix/fix-name main
```
- Created from `main` for emergencies
- Pattern: `hotfix/brief-description`
- Examples:
  - `hotfix/fix-critical-error`
  - `hotfix/security-xyz`
- Merges into both `main` AND `develop`

## Release 1.0 Workflow

### Phase 1: Development
1. Create features from `develop`:
   ```bash
   git checkout -b feature/functionality develop
   ```

2. Work on the feature and make commits

3. Open a Pull Request to merge into `develop`

4. After approval, merge:
   ```bash
   git checkout develop
   git merge --no-ff feature/functionality
   git push origin develop
   git branch -d feature/functionality
   ```

### Phase 2: Prepare Release
1. When `develop` is ready, create a release branch:
   ```bash
   git checkout -b release/1.0 develop
   ```

2. Update the version in `pom.xml`:
   ```xml
   <version>1.0.0</version>
   ```

3. Make a commit:
   ```bash
   git commit -am "Bump version to 1.0.0"
   ```

4. Only critical fixes, then:
   ```bash
   git push -u origin release/1.0
   ```

### Phase 3: Release
1. Create a Pull Request from `release/1.0` → `main`

2. After approval, merge into `main`:
   ```bash
   git checkout main
   git merge --no-ff release/1.0
   git tag -a v1.0.0 -m "Release version 1.0.0"
   git push origin main
   git push origin v1.0.0
   ```

3. Merge back into `develop`:
   ```bash
   git checkout develop
   git merge --no-ff release/1.0
   git push origin develop
   ```

4. Delete the release branch:
   ```bash
   git branch -d release/1.0
   git push origin --delete release/1.0
   ```

5. Update the version in `develop` for the next cycle:
   ```bash
   # In pom.xml
   <version>1.1.0-SNAPSHOT</version>
   ```

## Commit Conventions

Use the **Conventional Commits** pattern:

```
type(scope): brief description

feat: new feature
fix: bug fix
docs: documentation
style: formatting, no logic changes
refactor: refactoring without behavior changes
test: tests
ci: CI/CD
chore: dependencies, tools
```

Examples:
```
feat(parser): add HTML5 support
fix(crawler): fix timeout on slow connections
docs(readme): update installation instructions
```

## Current Status (28/03/2026)

✅ **main** - Created (production)
✅ **develop** - Created (for v1.0 work)

Next step: Create specific features for v1.0 from `develop`

## Git Flow Advantages

- ✅ Supports multiple production versions
- ✅ Controlled and planned releases
- ✅ Hotfixes don't affect development
- ✅ Main branch code is always stable
- ✅ Clean and organized commit history

