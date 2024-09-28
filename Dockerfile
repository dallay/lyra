FROM node:21-slim AS base
ENV PNPM_HOME="/pnpm"
ENV PATH="$PNPM_HOME:$PATH"
RUN corepack enable

# Build stage
FROM base AS build
COPY . /usr/src/app
WORKDIR /usr/src/app

# Install dependencies and build the project
RUN --mount=type=cache,id=pnpm,target=/pnpm/store pnpm install --frozen-lockfile
RUN pnpm run build

# Deploy output to specific directories
RUN pnpm deploy --filter=lyra-app --prod /prod/lyra-app
RUN pnpm deploy --filter=lyra-landing-page --prod /prod/lyra-landing-page

# Application image for lyra-app-dev (Nuxt)
FROM base AS lyra-app-dev
COPY --from=build /prod/lyra-app /prod/lyra-app-dev
WORKDIR /prod/lyra-app-dev

# Expose port for Nuxt server
EXPOSE 8000

# Start the Nuxt server
CMD [ "pnpm", "run", "start" ]

# Application image for lyra-app (Nuxt)
FROM base AS lyra-app
COPY --from=build /usr/src/app/apps/frontend/apps/lyra-app/.output /prod/lyra-app
WORKDIR /prod/lyra-app
# Expose port for Nuxt server
EXPOSE 7628

# Start node server
CMD [ "node", "./server/index.mjs" ]

# Application image for lyra-landing-page-dev (Astro)
FROM base AS lyra-landing-page-dev
COPY --from=build /prod/lyra-landing-page /prod/lyra-landing-page-dev
WORKDIR /prod/lyra-landing-page-dev

ENV HOST=0.0.0.0
ENV PORT=4321
EXPOSE 4321

# Start the static file server
CMD [ "pnpm", "run", "start" ]

# Application image for lyra-landing-page (Astro)
FROM base AS lyra-landing-page
COPY --from=build /usr/src/app/apps/frontend/apps/lyra-landing-page/dist /prod/lyra-landing-page
WORKDIR /prod/lyra-landing-page

ENV HOST=0.0.0.0
ENV PORT=4321
EXPOSE 4321

# Start node server
#CMD ["node", "./server/entry.mjs"]
CMD ["node", "./server/entry.mjs"]
