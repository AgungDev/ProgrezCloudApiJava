# Progrez Cloud API Java

### new update

Ada beberapa pembahruan seperti:
- Autentikasi pada saat mengambil project, di mana programnya melakukan 2x request. Setelah di lakukan pembahruan, sekarang saya hanya perlu melakukan 1x request untuk mendapatkan project.
- Ada beberapa fugsi yang bisa di tambahkan untuk mempermudah, dalam pengerjaan antarmuka pengguna.
- Saya membuang beberapa kodingan usang di versi sebelumnya.

### Setter

```
setUserKey(userkey): Fungsi untuk melakukan login dengan userkey
setUserLogin(username, password)
setProject(tokenproject, fields): Fungsi untuk mengubah detail proyek pada ProgrezCloudApi.
loadNewCridential(): Fungsi ini untuk mendapatkan akses baru, jika sewaktu2 akses lama usang
```

### Getter

```
login(listener): Membuat akun dan menyimpan Credential
loadNewCridential(): Re-login
getUsername(): Mengambil Username ketika return dari isLoginType(): false
getProject(): Fungsi untuk mendapatkan detail proyek setelah melakukan setProject(String tokenproject, String[] fields)
getProject(cridentials , listener, tokenProject, fields): Fungsi untuk mendapatkan detail proyek pada ProgrezCloudApi dengan menggunakan ProjectCallback.
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
ProgrezCloudApi api = new ProgrezCloudApi().setUserKey( USERKEY);
api.login((int errno, String errmsg, PCLoginModel account) -> {
    echo(account.getFullname());
});

api.setProject(TOKEN_PROJECT, new String[]{
    "all",      // Maintasks
    "all",      // Taks
    "all"       // Subtasks
});

if(api.getError() == 0){
    echo(api.getProject().getName());
}else{
    echo(api.getErrorMessage());
}
```
