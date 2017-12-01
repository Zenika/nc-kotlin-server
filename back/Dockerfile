FROM golang:1.9-alpine as builder
RUN apk add --no-cache git
RUN go get -v github.com/golang/dep/cmd/dep
ENV APP_DIR /go/src/github.com/Zenika/devfest-code-hero/back
RUN mkdir -p $APP_DIR
WORKDIR $APP_DIR
COPY . .
RUN dep ensure -v -vendor-only
RUN go install -v

FROM alpine:3.6
RUN apk add --no-cache curl
ENV APP_DIR /usr/src/back
RUN mkdir -p $APP_DIR
WORKDIR $APP_DIR
COPY --from=builder /go/bin/back .
EXPOSE 8080
ENTRYPOINT ["./back"]
