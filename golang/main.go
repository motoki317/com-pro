package main

import (
	"bufio"
	"os"
	"strconv"
)

var sc *bufio.Scanner

func init() {
	sc = bufio.NewScanner(os.Stdin)
	sc.Split(bufio.ScanWords)
}

func nextString() string {
	if !sc.Scan() {
		panic("no more input")
	}
	return sc.Text()
}

func nextInt() int {
	if !sc.Scan() {
		panic("no more input")
	}
	i, err := strconv.Atoi(sc.Text())
	if err != nil {
		panic(err)
	}
	return i
}

// --- Write code below ---

func main() {
}
