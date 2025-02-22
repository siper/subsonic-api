package ping

object PingResponse {
    val SUCCESS = """
        {
          "subsonic-response": {
            "status": "ok",
            "version": "1.16.1"
          }
        }
    """.trimIndent()

    val ERROR = """
        {
          "subsonic-response": {
            "status": "failed",
            "version": "1.15.0",
            "error": {
              "code": 10,
              "message": "Required parameter is missing."
            }
          }
        }
    """.trimIndent()
}