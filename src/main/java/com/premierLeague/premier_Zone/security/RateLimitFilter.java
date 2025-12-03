//package com.premierLeague.premier_Zone.security;
//
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Bucket4j;
//import io.github.bucket4j.Refill;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class RateLimitFilter extends OncePerRequestFilter {
//    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
//    private Bucket createBucket(){
//        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100 , Duration.ofMinutes(1)));
//        return Bucket4j.builder()
//                .addLimit(limit)
//                .build();
//    }
//    private String getClientId(HttpServletRequest request){
//        String xf = request.getHeader("X-Forwarded-For");
//        if(xf!=null && !xf.isEmpty()){
//            return xf.split(",")[0].trim();
//        }
//        return request.getRemoteAddr();
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String clientId = getClientId(request);
//        Bucket bucket = buckets.computeIfAbsent(clientId,k->createBucket());
//        if(bucket.tryConsume(1)){
//            long remaining = bucket.getAvailableTokens();
//            response.setHeader("X-RateLimit-Remaining",String.valueOf(remaining));
//            filterChain.doFilter(request,response);
//        }else {
//            response.setStatus(429);
//            response.setHeader(HttpHeaders.RETRY_AFTER,"60");
//            response.getWriter().write("Too Many Requests - Try Again After 1 minute.");
//        }
//    }
//}
