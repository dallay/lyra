FROM mcr.microsoft.com/playwright:v1.44.1-jammy

ENV HOME=/lyra-ui

WORKDIR ${HOME}
ADD . $HOME

RUN npm install -g pnpm
RUN pnpm install
