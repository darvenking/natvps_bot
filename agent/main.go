package main

import (
	"github.com/gin-gonic/gin"
	"github.com/spf13/viper"
)

func InitConfig() {
	viper.SetConfigName("config")
	viper.SetConfigType("ini")
	viper.AddConfigPath("./")

	err := viper.ReadInConfig()
	if err != nil {
		panic(err)
	}
}

func main() {
	InitConfig()
	host := viper.GetString("server.HTTP_HOST")
	port := viper.GetString("server.HTTP_PORT")
	mode := viper.GetString("app.MODE")
	ssl := viper.GetString("app.SSL")
	crtFile := viper.GetString("app.SSL_CRT_FILE")
	keyFile := viper.GetString("app.SSL_KEY_FILE")

	gin.SetMode(mode)
	r := gin.Default()
	var err error
	if ssl == "on" {
		err = r.RunTLS(host+":"+port, crtFile, keyFile)
	} else {
		err = r.Run(host + ":" + port)
	}
	if err != nil {
		panic(err)
	}
}
