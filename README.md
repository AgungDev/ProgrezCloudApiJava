# Progrez Cloud API Java

```java
ProgrezCloudApi progrezCloudApi = new ProgrezCloudApi(); // initial class
progrezCloudApi.login((int errno, String errmsg, PCLoginModel account) -> {
  if (errno == 0)
    progrezCloudApi.getProject(account, (int errno2, String errmsg2, PCProjectModel body) -> {
      setNama(account.getFullName());             // menampilkan namamu
      setGambarProfile(account.getProfile());     // menampilkan gambar profile
      tampilkanProject(body);                     // menampilkan project
    },"TOKEN_PROJECT", new String[]{
      "task_name, tasktype",       // maintask
      "all",                       // task
      "privacy, nominal"           // subtask
    });
    
},"USER_KEY");
```

