FROM golang:1.16.3-alpine

WORKDIR /app

COPY . .

RUN go build main.go

# Run the executable
CMD ["./main"]