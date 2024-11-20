# syntax=docker/dockerfile:1
FROM node:23-alpine AS base

# Metadata for the image
LABEL maintainer="05barkers.airbase@icloud.com"
LABEL org.opencontainers.image.source="https://github.com/dallay/lyra"
LABEL org.opencontainers.image.title="Lyra Project"
LABEL org.opencontainers.image.description="Multi-platform Docker images for all Lyra applications"
LABEL org.opencontainers.image.licenses="MIT"

# Environment setup for Node.js and PNPM
ENV PNPM_HOME="/pnpm"
ENV PATH="$PNPM_HOME:$PATH"
RUN corepack enable

# Build stage for frontend
FROM base AS build-frontend
COPY . /usr/src/app
WORKDIR /usr/src/app

# Use Docker secrets and cache to handle sensitive data and pnpm cache
RUN --mount=type=secret,id=tiptap_pro_token,env=TIPTAP_PRO_TOKEN \
    --mount=type=cache,id=pnpm,target=/pnpm/store \
    pnpm install --frozen-lockfile
RUN pnpm run build

# Deploy output to specific directories for lyra-app and lyra-landing-page
RUN --mount=type=secret,id=tiptap_pro_token,env=TIPTAP_PRO_TOKEN \
    pnpm deploy --filter=lyra-app --prod /prod/lyra-app
RUN --mount=type=secret,id=tiptap_pro_token,env=TIPTAP_PRO_TOKEN \
    pnpm deploy --filter=lyra-landing-page --prod /prod/lyra-landing-page

# Application image for lyra-app-dev (Nuxt) - Development
FROM base AS lyra-app-dev
LABEL stage="development"
COPY --from=build-frontend /prod/lyra-app /prod/lyra-app-dev
WORKDIR /prod/lyra-app-dev

# Expose port for Nuxt server in development
EXPOSE 3000

# Start the Nuxt server in development mode
CMD [ "pnpm", "run", "start" ]

# Application image for lyra-app (Nuxt) - Production
FROM gcr.io/distroless/nodejs22-debian12 AS lyra-app
LABEL stage="production"
WORKDIR /prod/lyra-app

# Copy only the production build output from the build stage
COPY --from=build-frontend /usr/src/app/apps/frontend/apps/lyra-app/.output /prod/lyra-app

# Expose port for Nuxt server in production
EXPOSE 3000/tcp

# Start the Nuxt server in production mode
CMD [ "./server/index.mjs" ]

# Application image for lyra-landing-page-dev (Astro) - Development
FROM base AS lyra-landing-page-dev
LABEL stage="development"
COPY --from=build-frontend /prod/lyra-landing-page /prod/lyra-landing-page-dev
WORKDIR /prod/lyra-landing-page-dev

# Set environment variables for Astro development
ENV HOST=0.0.0.0
ENV PORT=4321

# Expose port for Astro server in development
EXPOSE 4321

# Start the Astro server in development mode
CMD [ "pnpm", "run", "start" ]

# Application image for lyra-landing-page (Astro) - Production
FROM gcr.io/distroless/nodejs22-debian12 AS lyra-landing-page
LABEL stage="production"
WORKDIR /prod/lyra-landing-page

# Copy only the production build output from the build stage
COPY --from=build-frontend /usr/src/app/apps/frontend/apps/lyra-landing-page/dist /prod/lyra-landing-page

# Set environment variables for Astro production
ENV HOST=0.0.0.0
ENV PORT=4321

# Expose port for Astro server in production
EXPOSE 4321

# Start the Astro static file server in production mode
CMD ["./server/entry.mjs"]
