FROM node:8-alpine as builder
WORKDIR /usr/src/app/
COPY package.json yarn.lock ./
RUN yarn --pure-lockfile
COPY . ./
RUN yarn build

FROM node:8-alpine
ENV APP_DIR /usr/src/app
WORKDIR $APP_DIR
COPY --from=builder $APP_DIR/package.json $APP_DIR/yarn.lock $APP_DIR/config.json $APP_DIR/build/ ./
RUN yarn --pure-lockfile --production
EXPOSE 3000
ENTRYPOINT ["node", "index.js"]
