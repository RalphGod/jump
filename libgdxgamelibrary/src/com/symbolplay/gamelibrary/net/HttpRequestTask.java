package com.symbolplay.gamelibrary.net;

import com.badlogic.gdx.utils.Array;

public final class HttpRequestTask implements Runnable {
    private Thread thread;
    private final String threadName;
    
    private final String urlString;
    private final Array<QueryParameter> queryParameters;
    private final boolean isByteArrayResult;
    private final int estimatedResultSize;
    private final boolean isPost;
    
    private Object data;
    
    private HttpRequestTaskFinishedListener finishedListener; 
    
    public HttpRequestTask(String threadName, String urlString, Array<QueryParameter> queryParameters, boolean isByteArrayResult, int estimatedResultSize, boolean isPost) {
        this.threadName = threadName;
        this.urlString = urlString;
        this.queryParameters = queryParameters;
        this.isByteArrayResult = isByteArrayResult;
        this.estimatedResultSize = estimatedResultSize;
        this.isPost = isPost;
    }
    
    public void run() {
        if (!isPost) {
            data = NetUtils.executeGetRequest(urlString, queryParameters, isByteArrayResult, estimatedResultSize);
        } else {
            data = NetUtils.executePostRequest(urlString, queryParameters, isByteArrayResult, estimatedResultSize);
        }
        notifyFinished();
    }
    
    public void start() {
        if (thread == null)
        {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }
    
    public Object getData() {
        return data;
    }
    
    public void setFinishedListener(HttpRequestTaskFinishedListener finishedListener) {
        this.finishedListener = finishedListener;
    }
    
    private void notifyFinished() {
        if (finishedListener != null) {
            finishedListener.httpRequestTaskFinished();
        }
    }
}
