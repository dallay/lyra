FROM mcr.microsoft.com/playwright:v1.44.0-jammy

ENV HOME=/lyra-ui

WORKDIR ${HOME}
ADD . $HOME

RUN npm install -g pnpm
RUN pnpm install
