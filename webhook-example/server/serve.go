package main

import (
    "fmt"
    "net/http"
)

func main() {
    http.HandleFunc("/", func (w http.ResponseWriter, r *http.Request) {
		fmt.Fprintf(w, "Hello World v3, %s!", r.URL.Path[1:])
	})
    http.ListenAndServe(":8080", nil)
}

