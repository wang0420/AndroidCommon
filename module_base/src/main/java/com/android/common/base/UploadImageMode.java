package com.android.common.base;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/6/3.
 */
public class UploadImageMode {

    /*      String url = value.getUrl();
                if (!TextUtils.isEmpty(url) && !url.contains("http")) {
//                VerifyService.getInstance().uploadImage(url);
//                    VerifyService.getInstance().uploadImageProgress(url,progressListener);
                    mPresenter.uploadImg(url, new ProgressListener() {
                        @Override
                        public void onProgress(long totalBytes, long remainingBytes, boolean done) {

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    showLoadingDialog("上传中...请稍等..." + "\n" +
                                            "上传进度" + (totalBytes - remainingBytes) * 100 / totalBytes + "% " + "  剩余大小" + remainingBytes / 1024 + "KB");
                                }
                            });
                        }
                    });
                    return;
                }*/


    //////////////////////////Presenter/////////////////////////////


    /*
    *
    public class VerifyPresenter extends BasePresenter<VerifyContact.View, VerifyModel> implements VerifyContact.Presenter {
        @Override
        public VerifyModel getModelInstance() {
            return ViewModelFactory.getInstance(VerifyModel.class, mView);
        }

        public VerifyPresenter(VerifyContact.View view) {
            super(view);
        }


        @Override
        public void uploadImg(String imgUrl, ProgressListener progressListener) {
            MutableLiveData<BaseResponseBean<UploadImageRes>> uploadImgModel = mModel.uploadImg(imgUrl, progressListener);
            uploadImgModel.observe((LifecycleOwner) mView, uploadModelObserver);
        }

        ModelObserver uploadModelObserver = new ModelObserver<UploadImageRes>(mView) {
            @Override
            public void onSuccess(UploadImageRes data) {
                ZALog.i("VerifyPresenter.onSuccess   >> " + data);
                if (data != null) {
                    mView.showUoloadImg(data);
                }

            }

            @Override
            public void onFailed(int code, String message) {
                ZALog.i("VerifyPresenter.onFailed   >> " + message);
                super.onFailed(code, message);
                mView.showUploadImgFailed(message);

            }
        };

        @Override
        public void saveImg(HashMap<String, Object> params) {
            MutableLiveData<BaseResponseBean<Object>> uploadImgModel = mModel.saveImg(params);
            uploadImgModel.observe((LifecycleOwner) mView, saveModelObserver);
        }

        ModelObserver saveModelObserver = new ModelObserver<Object>(mView) {
            @Override
            public void onSuccess(Object data) {
//            if (data != null) {
                mView.showSaveImg(data);
//            }
            }
        };

        @Override
        public void getImg(String memberId) {
            MutableLiveData<BaseResponseBean<VerifyPhotoBean>> getImgModel = mModel.getImg(memberId);
            getImgModel.observe((LifecycleOwner) mView, getModelObserver);
        }

        @Override
        public void deleteImg(String memberId, String autoId) {
            MutableLiveData<BaseResponseBean<VerifyPhotoBean>> deleteImgModel = mModel.deleteImg(memberId, autoId);
            deleteImgModel.observe((LifecycleOwner) mView, deleteModelObserver);
        }

        ModelObserver deleteModelObserver = new ModelObserver<Object>(mView) {
            @Override
            public void onSuccess(Object verifyPhotoBean) {
                mView.showDeleteImg("删除成功");
            }
        };

        ModelObserver getModelObserver = new ModelObserver<VerifyPhotoBean>(mView) {
            @Override
            public void onSuccess(VerifyPhotoBean verifyPhotoBean) {
                if (verifyPhotoBean != null) {
                    if (verifyPhotoBean.getCertificateDetailList() != null && verifyPhotoBean.getCertificateDetailList().size() > 0) {
                        for (ImageBean imageBean : verifyPhotoBean.getCertificateDetailList()) {
                            int type = imageBean.getType();
                            if (type > 6) {
                                verifyPhotoBean.getOtherPhoto().add(imageBean);
                            } else {
                                verifyPhotoBean.getVerifyPhotos().put(type, imageBean);
                            }
                        }
                    }

                    mView.showGetImg(verifyPhotoBean);
                }
            }
        };

    }

    * */


    ///////////////////////////mode//////////////////



 /*

   public class VerifyModel extends BaseModel {

    private MutableLiveData<BaseResponseBean<UploadImageRes>> verifyLiveData;

    private MutableLiveData<BaseResponseBean<Object>> saveLiveData;
    private MutableLiveData<BaseResponseBean<VerifyPhotoBean>> getImgLiveData;
    private MutableLiveData<BaseResponseBean<VerifyPhotoBean>> deleteImgLiveData;

    public VerifyModel(Application application) {
        super(application);
    }


      图片上传 并监听上传进度

    public MutableLiveData<BaseResponseBean<UploadImageRes>> uploadImg(String imgUrl, ProgressListener progressListener) {
        if (verifyLiveData == null) {
            verifyLiveData = new MutableLiveData<>();
        }
        IVipMemberService iVipMemberService = getService(IVipMemberService.class);
        Map<String, Object> param = new HashMap<>();
        File file = new File(imgUrl);//filePath 图片地址
        param.put("image", file);
        param.put("memberId", SharedPreferenceService.getMemberId());
        MultipartBody multipartBody = buildMultipartFileBody(param, progressListener);
        Observable<BaseResponseBean<UploadImageRes>> uploadImgModel = iVipMemberService.uploadImg(multipartBody.parts());
        getRequestData(uploadImgModel, verifyLiveData);
        return verifyLiveData;
    }

    public MultipartBody buildMultipartFileBody(Map<String, Object> params, ProgressListener progressListener) {
        MultipartBody.Builder builder = new MultipartBody.Builder("**-----------**").setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
            Object value = stringObjectEntry.getValue();
            if (value instanceof String) {
                builder.addFormDataPart(stringObjectEntry.getKey(), (String) stringObjectEntry.getValue());
            }
            if (value instanceof Integer)
                builder.addFormDataPart(stringObjectEntry.getKey(), stringObjectEntry.getValue() + "");
            if (value instanceof JSONObject)
                builder.addFormDataPart(stringObjectEntry.getKey(), stringObjectEntry.getValue().toString());
            else if (value instanceof File) {
                File value1 = (File) value;
                builder.addFormDataPart(stringObjectEntry.getKey(), value1.getName(), createCustomRequestBody(MediaType.parse("application/octet-stream"), value1, progressListener));
            }
        }
        MultipartBody build = builder.build();
        return build;
    }

    public static RequestBody createCustomRequestBody(final MediaType contentType, final File file, final ProgressListener listener) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    //sink.writeAll(source);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
*/
}
