package com.currenzy.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * An OkHttp interceptor for adding custom headers to HTTP requests.
 *
 * This interceptor takes a map of header names and values and adds them to each outgoing HTTP request.
 * It allows for the injection of custom headers, such as API keys, for authentication or other purposes.
 *
 * @param headers A map of header names and their corresponding values to be added to HTTP requests.
 */
class HeadersInterceptor(private val headers: Map<String, String>) : Interceptor {

    /**
     * Intercepts an HTTP request to add custom headers.
     *
     * This method is called for each request that passes through the interceptor. It creates a new request
     * with the specified headers and then proceeds with the request using the modified request.
     *
     * @param chain The interceptor chain used to proceed with the request.
     * @return The HTTP response received from the server.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        // Create a new request builder to add headers
        val request = chain.request().newBuilder()

        // Add each header from the map to the request
        for ((headerName, headerValue) in headers) {
            request.addHeader(headerName, headerValue)
        }

        // Build the request with the added headers and proceed with the chain
        return chain.proceed(request.build())
    }
}