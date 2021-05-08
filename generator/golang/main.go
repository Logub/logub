package main

import (
    "log"
    "time"
    "math/rand"
)

var letters = []rune("abcdefghijklmnopkrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

func getRandomString(length int) string {
    r := make([]rune, length)
    for i := range r {
        r[i] = letters[rand.Intn(len(letters))]
    }
    return string(r)
}

func main() {
    rand.Seed(time.Now().UnixNano())
    for {
        log.Println(getRandomString(50))
        time.Sleep(5 * time.Second)
    }
}