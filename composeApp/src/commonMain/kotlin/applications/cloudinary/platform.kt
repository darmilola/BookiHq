package applications.cloudinary

expect fun uploadImage(filePath: String, onUploadSuccessful : (String) -> Unit, onUploadStarted: (Boolean) -> Unit,
                       onProgress : (Long, Long) -> Unit, onError : (String) -> Unit)