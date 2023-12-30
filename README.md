<div align="center">
<img src="src/main/webapp/public/favicon.svg" height="50px"/> 
<h1>
 Lyra
</h1>
<p>Created for didactic and educational purposes.</p>
</div>

<div align="center">
    <a href="#" target="_blank">
        Preview
    </a>
    <span>&nbsp;✦&nbsp;</span>
    <a href="#-getting-started">
        Getting Started
    </a>
    <span>&nbsp;✦&nbsp;</span>
    <a href="#-commands">
        Commands
    </a>
    <span>&nbsp;✦&nbsp;</span>
    <a href="#-license">
        License
    </a>
    <span>&nbsp;✦&nbsp;</span>
    <a href="https://twitter.com/yacosta738">
        Twitter
    </a>
</div>

<p></p>

<div align="center">

![Astro Badge](https://img.shields.io/badge/Astro-BC52EE?logo=astro&logoColor=fff&style=flat)
![Typescript Badge](https://img.shields.io/badge/Typescript-007ACC?logo=typescript&logoColor=fff&style=flat)
![GitHub stars](https://img.shields.io/github/stars/yacosta738/lyra)
![GitHub issues](https://img.shields.io/github/issues/yacosta738/lyra)
![GitHub forks](https://img.shields.io/github/forks/yacosta738/lyra)
![GitHub PRs](https://img.shields.io/github/issues-pr/yacosta738/lyra)
![Tailwind CSS Badge](https://img.shields.io/badge/Tailwind%20CSS-06B6D4?logo=tailwindcss&logoColor=fff&style=flat)
![Spring Boot Badge](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=flat)
![Kotlin Badge](https://img.shields.io/badge/Kotlin-0095D5?logo=kotlin&logoColor=fff&style=flat)
![Gradle Badge](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=fff&style=flat)

</div>

> [!WARNING]
> This page is not official. The official page is [**premiosesland.com**](https://premiosesland.com/).

## 🛠️ Stack

- [**Astro**](https://astro.build/) - The web framework for content-driven websites.
- [**Typescript**](https://www.typescriptlang.org/) - JavaScript with syntax for types.
- [**Tailwindcss**](https://tailwindcss.com/) - A utility-first CSS framework for rapidly building custom designs.
- [**tailwindcss-animated**](https://github.com/new-data-services/tailwindcss-animated) - Extended animation utilities for Tailwind CSS.
- [**fontsource**](https://fontsource.org/) - Self-host Open Source fonts in neatly bundled NPM packages.

# Astro Starter Kit: Basics

[![Open in StackBlitz](https://developer.stackblitz.com/img/open_in_stackblitz.svg)](https://stackblitz.com/github/withastro/astro/tree/latest/examples/basics)
[![Open with CodeSandbox](https://assets.codesandbox.io/github/button-edit-lime.svg)](https://codesandbox.io/p/sandbox/github/withastro/astro/tree/latest/examples/basics)
[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/withastro/astro?devcontainer_path=.devcontainer/basics/devcontainer.json)

> 🧑‍🚀 **Seasoned astronaut?** Delete this file. Have fun!

![just-the-basics](https://github.com/withastro/astro/assets/2244813/a0a5533c-a856-4198-8470-2d67b1d7c554)

## 🚀 Project Structure

Inside of your Astro project, you'll see the following folders and files:

```text
.
├── CHANGELOG.md
├── HELP.md
├── README.md
├── astro.config.mjs
├── build.gradle.kts
├── compose.yaml
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── package.json
├── pnpm-lock.yaml
├── renovate.json
├── settings.gradle.kts
├── src
│   ├── main
│   │   ├── kotlin
│   │   │   └── com
│   │   │       └── lyra
│   │   │           └── app
│   │   │               ├── LyraApplication.kt
│   │   │               └── config
│   │   │                   └── WebConfig.kt
│   │   ├── resources
│   │   │   └── application.properties
│   │   └── webapp
│   │       ├── node_modules
│   │       ├── public
│   │       │   └── favicon.svg
│   │       └── src
│   │           ├── components
│   │           │   └── Card.astro
│   │           ├── env.d.ts
│   │           ├── layouts
│   │           │   └── Layout.astro
│   │           └── pages
│   │               └── index.astro
│   └── test
│       └── kotlin
│           └── com
│               └── lyra
│                   └── app
│                       └── LyraApplicationTests.kt
├── tailwind.config.mjs
└── tsconfig.json
```

## Developer Guide

Make sure you have setup your local Git Hooks:

```sh
git config core.hooksPath .githooks
```

This will make sure your commit messages follow the [Conventional Commits Specification](https://www.conventionalcommits.org/en/v1.0.0/).


Astro looks for `.astro` or `.md` files in the `src/pages/` directory. Each page is exposed as a route based on its file name.

There's nothing special about `src/components/`, but that's where we like to put any Astro/React/Vue/Svelte/Preact components.

Any static assets, like images, can be placed in the `public/` directory.

## 🧞 Commands

All commands are run from the root of the project, from a terminal:

| Command                   | Action                                           |
| :------------------------ | :----------------------------------------------- |
| `npm install`             | Installs dependencies                            |
| `npm run dev`             | Starts local dev server at `localhost:4321`      |
| `npm run build`           | Build your production site to `./dist/`          |
| `npm run preview`         | Preview your build locally, before deploying     |
| `npm run astro ...`       | Run CLI commands like `astro add`, `astro check` |
| `npm run astro -- --help` | Get help using the Astro CLI                     |

## 👀 Want to learn more?

Feel free to check [our documentation](https://docs.astro.build) or jump into our [Discord server](https://astro.build/chat).
