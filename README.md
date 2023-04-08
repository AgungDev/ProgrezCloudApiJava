# Progrez Cloud API Java

# new update fungsi

## new update fungsi

### Setter

```
setUserKey(String userkey): Fungsi untuk melakukan login dengan userkey
setProject(String tokenproject, String[] fields): Fungsi untuk mengubah detail proyek pada ProgrezCloudApi.
loadNewCridential(): Fungsi ini untuk mendapatkan akses baru, jika sewaktu2 akses lama usang
```

### Getter

```
getUsername(): Mengambil Username ketika return dari isLoginType(): false
getProject(): Fungsi untuk mendapatkan detail proyek setelah melakukan setProject(String tokenproject, String[] fields)
getProject(PCLoginModel account,ProjectCallback a, String tokenProject, String[] fields): Fungsi untuk mendapatkan detail proyek pada ProgrezCloudApi dengan menggunakan ProjectCallback.
getMaintasks(): Fungsi untuk mendapatkan daftar tugas utama dalam suatu proyek.
isLoginType(): Menentukan login mengunakan (userkey): true, atau mengunakan (username,password): false
getUserkey(): Fungsi untuk mendapatkan kunci pengguna ProgrezCloudApi.
getProfileUser(): Fungsi untuk mendapatkan model login pengguna.
getCredentials(): Fungsi untuk mendapatkan kredensial pengguna ProgrezCloudApi.
getError(): Fungsi untuk mendapatkan kode kesalahan saat melakukan permintaan ke ProgrezCloudApi.
getErrorMessage(): Fungsi untuk mendapatkan pesan kesalahan saat melakukan permintaan ke ProgrezCloudApi.
```

## contoh

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
