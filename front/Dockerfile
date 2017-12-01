FROM node:8-alpine as builder
WORKDIR /usr/src/app/
COPY package.json yarn.lock ./
RUN yarn --pure-lockfile
COPY . ./
RUN yarn build

FROM nginx:1-alpine
COPY --from=builder /usr/src/app/dist/ /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
