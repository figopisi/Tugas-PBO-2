<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <h1><strong>Pembuatan API Pemesanan Vila Sederhana Berbasis Java</strong></h1>
    </header>
    <div>
        <p align="justify">Halo! Program ini kami rancang untuk menyelesaikan tugas dari mata kuliah PBO (Pemrograman Berbasis Objek) kami.</p>
        <div>
            <h3>Perkenalkan kami:</h3>
            <ul>
                <li><strong>Ni Made Rita Mutiara Dewi</strong> (2405551009 || PBO B)</li>
                <li><strong>I Gde Made Fajar Harry Putra Utama</strong> (2405551025 || PBO B)</li>
                <li><strong>Anak Agung Ngurah Figo Laksmana Saputra</strong> (2405551056 || PBO B)</li>
                <li><strong>Putu Lidya Paramita Sunu</strong> (2405551075 || PBO B)</li>
            </ul>
        </div>
        <p align="justify">Program ini adalah aplikasi API sederhana berbasis command line dan HTTP server yang memungkinkan pengguna untuk melakukan pemesanan villa, mengelola data villa, customer, dan voucher. Program ini menggunakan bahasa pemrograman Java yang dirancang untuk tiga entitas utama dengan hak akses yang berbeda, yaitu:</p>
        <ol>
            <li><strong>Villa</strong> untuk pengelolaan data vila, kamar, ketersediaan, booking, dan review.</li>
            <li><strong>Customer</strong> untuk registrasi, melihat portofolio booking, memberikan review, dan memesan villa.</li>
            <li><strong>Voucher</strong> untuk pengelolaan diskon berupa voucher pada pemesanan.</li>
        </ol>
        <p align="justify">Program ini menyediakan endpoint HTTP (RESTful API) yang memungkinkan pengguna untuk melakukan berbagai operasi, seperti:</p>
        <ol>
            <li>Melihat daftar villa, detail villa, kamar yang tersedia, review, hingga mengecek ketersediaan berdasarkan tanggal.</li>
            <li>Menambahkan, memperbarui, atau menghapus data villa, kamar, customer, dan voucher.</li>
            <li>Customer dapat mendaftarkan diri, melakukan pemesanan villa, dan memberikan review.</li>
        </ol>
        <p align="justify">Program ini juga dilengkapi dengan validasi input untuk memastikan bahwa data yang dikirimkan pengguna sesuai dengan yang dibutuhkan oleh program, serta memberikan response dalam format JSON.</p>
        <p align="justify">Aplikasi ini ditujukan sebagai contoh sederhana implementasi backend pemesanan villa dengan arsitektur modular berbasis Java, cocok untuk pembelajaran konsep REST API, validasi data, dan pengelolaan entitas melalui HTTP.</p>
        <p align="justify">Berikut uraian sistem yang telah dilengkapi komentar untuk mempermudah pemahaman alur pemakaian program. Silahkan disimak!</p>
    </div>
    <div>
        <h2><strong>SISTEM PEMAKAIAN PROGRAM</strong></h2>
        <p align="justify">Berikut sistem pemakaian program menggunakan aplikasi PostMan beserta tampilan hasil eksekusi programnya.</p>
        <h2><strong>1. Villa</strong></h2>
        <div>
            <h3>GET /villas</h3>
            <img src="assets2/Get villas.png" alt="Testing endpoint GET /villas dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi PostMan untuk melakukan testing API endpoint GET /villas yang berfungsi untuk mengambil dan menampilkan daftar semua villa yang tersedia dalam sistem booking atau manajemen villa. Endpoint ini biasanya digunakan untuk menampilkan katalog villa kepada pengguna yang ingin melihat pilihan akomodasi yang tersedia, lengkap dengan informasi seperti id, name, description, dan address dari setiap villa yang tersimpan dalam database. PostMan digunakan sebagai tool untuk testing API dengan cara membuat request baru, memilih method GET, memasukkan URL endpoint /villas, dan mengirim request untuk mendapatkan response dari server. Penggunaan PostMan ini memungkinkan developer untuk memverifikasi bahwa API endpoint berfungsi dengan baik dan mengembalikan data villa yang sesuai dengan struktur database.</p>
        </div>
        <div>
            <h3>GET /villas/{id}</h3>
            <img src="assets2/Get villas_{id}.png" alt="Testing endpoint GET /villas/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /villas/{id} yang berfungsi untuk mengambil dan menampilkan detail sebuah villa berdasarkan ID yang diberikan. Endpoint ini digunakan untuk menampilkan informasi lengkap villa, seperti id, name, description, dan address, yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan ID, lalu mengirim request untuk mendapatkan response detail dari server.</p>
        </div>
        <div>
            <h3>GET /villas/{id}/rooms</h3>
            <img src="assets2/Get rooms.png" alt="Testing endpoint GET /villas/{id}/rooms dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /villas/{id}/rooms yang berfungsi untuk mengambil dan menampilkan daftar kamar pada sebuah villa tertentu lengkap dengan fasilitas dan harga masing-masing kamar. Endpoint ini digunakan untuk melihat detail tipe-tipe kamar yang tersedia di sebuah villa beserta informasi pendukungnya yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan ID villa, lalu mengirim request untuk mendapatkan response berisi data kamar dari server.</p>
        </div>
        <div>
            <h3>GET /villas/{id}/bookings</h3>
            <img src="assets2/Get villas_{id}_bookings.png" alt="Testing endpoint GET /villas/{id}/bookings dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /villas/{id}/bookings yang berfungsi untuk mengambil dan menampilkan daftar semua booking yang pernah dilakukan pada sebuah villa tertentu. Endpoint ini digunakan untuk melihat riwayat pemesanan pada villa, lengkap dengan data booking yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan ID villa, lalu mengirim request untuk mendapatkan response berisi daftar booking dari server.</p>
        </div>
        <div>
            <h3>GET /villas/{id}/reviews</h3>
            <img src="assets2/Get villas_{id}_reviews.png" alt="Testing endpoint GET /villas/{id}/reviews dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /villas/{id}/reviews yang berfungsi untuk mengambil dan menampilkan daftar semua review yang diberikan pada sebuah villa tertentu. Endpoint ini digunakan untuk melihat ulasan-ulasan yang tersimpan di database untuk villa tersebut. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan ID villa, lalu mengirim request untuk mendapatkan response berisi daftar review dari server.</p>
        </div>
        <div>
            <h3>GET /villas?ci_date={checkin_date}&co_date={checkout_date}</h3>
            <img src="assets2/Get villa availability (ciDate and coDate) c.png" alt="Testing endpoint GET /villas dengan query parameter tanggal" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /villas?ci_date={checkin_date}&co_date={checkout_date} yang berfungsi untuk mencari dan menampilkan daftar vila yang tersedia berdasarkan rentang tanggal check-in dan checkout yang diberikan. Endpoint ini digunakan untuk membantu pengguna melihat vila mana saja yang masih tersedia pada periode tertentu dengan data yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan parameter tanggal, misalnya /villas?ci_date=2025-07-10&co_date=2025-07-15, lalu mengirim request untuk mendapatkan respon berupa daftar villa dan kamar yang tersedia dari server.</p>
        </div>
        <div>
            <h3>POST /villas</h3>
            <img src="assets2/Post villas.png" alt="Testing endpoint POST /villas dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint POST /villas yang berfungsi untuk menambahkan data sebuah villa baru ke dalam sistem. Endpoint ini digunakan untuk menyimpan informasi villa baru, seperti name, description, dan address, ke database. Postman digunakan dengan membuat request baru, memilih method POST, memasukkan URL endpoint /villas, lalu pada bagian Body memilih format raw JSON dan mengisi data villa sebelum mengirim request untuk mendapatkan response dari server.</p>
        </div>
        <div>
            <h3>POST /villas/{id}/rooms</h3>
            <img src="assets2/Put rooms.png" alt="Testing endpoint POST /villas/{id}/rooms dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint POST /villas/{id}/rooms yang berfungsi untuk menambahkan tipe kamar baru pada sebuah villa tertentu. Endpoint ini digunakan untuk menyimpan informasi kamar, seperti nama kamar, kapasitas, harga, dan fasilitas, ke dalam database untuk villa dengan ID yang diberikan. Postman digunakan dengan membuat request baru, memilih method POST, memasukkan URL endpoint lengkap dengan ID villa, lalu pada bagian Body memilih format raw JSON dan mengisi data kamar sebelum mengirim request untuk mendapatkan response dari server.</p>
        </div>
        <div>
            <h3>PUT /villas/{id}</h3>
            <img src="assets2/Put villas_{id}.png" alt="Testing endpoint PUT /villas/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint PUT /villas/{id} yang berfungsi untuk mengubah atau memperbarui data sebuah villa tertentu berdasarkan ID. Endpoint ini digunakan untuk memperbarui informasi villa seperti name, description, atau address yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method PUT, memasukkan URL endpoint lengkap dengan ID villa, lalu pada bagian Body memilih format raw JSON dan mengisi data baru villa sebelum mengirim request untuk mendapatkan response dari server.</p>
        </div>
        <div>
            <h3>PUT /villas/{id}/rooms/{id}</h3>
            <img src="assets2/Put rooms_{id}.png" alt="Testing endpoint PUT /villas/{id}/rooms/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint PUT /villas/{id}/rooms/{id} yang berfungsi untuk mengubah atau memperbarui informasi kamar tertentu pada sebuah villa. Endpoint ini digunakan untuk memperbarui data kamar seperti nama, kapasitas, harga, atau fasilitas yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method PUT, memasukkan URL endpoint lengkap dengan ID villa dan ID kamar, lalu pada bagian Body memilih format raw JSON dan mengisi data baru kamar sebelum mengirim request untuk mendapatkan response dari server.</p>
        </div>
        <div>
            <h3>DELETE /villas/{id}/rooms/{id}</h3>
            <img src="assets2/Delete rooms_{id}.png" alt="Testing endpoint DELETE /villas/{id}/rooms/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint DELETE /villas/{id}/rooms/{id} yang berfungsi untuk menghapus sebuah kamar tertentu dari sebuah villa. Endpoint ini digunakan untuk menghapus data kamar yang tersimpan di database berdasarkan ID villa dan ID kamar yang diberikan. Postman digunakan dengan membuat request baru, memilih method DELETE, memasukkan URL endpoint lengkap dengan ID villa dan ID kamar, lalu mengirim request untuk mendapatkan response konfirmasi dari server.</p>
        </div>
        <h2><strong>2. Customer</strong></h2>
        <div>
            <h3>GET /customers</h3>
            <img src="assets2/Get customer (Daftar semua customer).png" alt="Testing endpoint GET /customers dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /customers yang berfungsi untuk mengambil dan menampilkan daftar semua customer yang terdaftar dalam sistem. Endpoint ini digunakan untuk melihat seluruh data customer yang tersimpan di database, lengkap dengan informasi seperti id, name, email, dan phone. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint /customers, lalu mengirim request untuk mendapatkan response berupa daftar customer dari server.</p>
        </div>
        <div>
            <h3>GET /customers/{id}</h3>
            <img src="assets2/Get customers_{id}.png" alt="Testing endpoint GET /customers/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /customers/{id} yang berfungsi untuk mengambil dan menampilkan informasi detail dari seorang customer berdasarkan ID yang diberikan. Endpoint ini digunakan untuk melihat data lengkap customer, seperti id, name, email, dan phone, yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan ID customer, lalu mengirim request untuk mendapatkan response berisi detail customer dari server.</p>
        </div>
        <div>
            <h3>GET /customers/{id}/bookings</h3>
            <img src="assets2/Get customer_{id}_bookings.png" alt="Testing endpoint GET /customers/{id}/bookings dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /customers/{id}/bookings yang berfungsi untuk mengambil dan menampilkan daftar semua booking yang telah dilakukan oleh seorang customer tertentu. Endpoint ini digunakan untuk melihat riwayat pemesanan customer beserta detail booking yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan ID customer, lalu mengirim request untuk mendapatkan response berupa daftar booking dari server.</p>
        </div>
        <div>
            <h3>GET /customers/{id}/reviews</h3>
            <img src="assets2/Get Customer_{id}_Reviews.png" alt="Testing endpoint GET /customers/{id}/reviews dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /customers/{id}/reviews yang berfungsi untuk mengambil dan menampilkan daftar semua ulasan yang telah diberikan oleh seorang customer tertentu. Endpoint ini digunakan untuk melihat riwayat review yang dibuat customer pada berbagai villa, lengkap dengan detail ulasan yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan ID customer, lalu mengirim request untuk mendapatkan response berupa daftar ulasan dari server.</p>
        </div>
        <div>
            <h3>POST /customers</h3>
            <img src="assets2/Post Customer (menambahkan Cust).png" alt="Testing endpoint POST /customers dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint POST /customers yang berfungsi untuk menambahkan data customer baru ke dalam sistem (registrasi customer). Endpoint ini digunakan untuk menyimpan informasi customer seperti name, email, dan phone ke database. Postman digunakan dengan membuat request baru, memilih method POST, memasukkan URL endpoint /customers, lalu pada bagian Body memilih format raw JSON dan mengisi data customer sebelum mengirim request untuk mendapatkan response dari server.</p>
        </div>
        <div>
            <h3>POST /customers/{id}/bookings</h3>
            <img src="assets2/Post Customer (melakukan pemesanan villa).png" alt="Testing endpoint POST /customers/{id}/bookings dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint POST /customers/{id}/bookings yang berfungsi untuk membuat pemesanan vila oleh seorang customer berdasarkan ID customer yang diberikan. Endpoint ini digunakan untuk menyimpan data booking baru ke dalam database, termasuk informasi seperti ID kamar, tanggal check-in, tanggal check-out, dan optional voucher. Postman digunakan dengan membuat request baru, memilih method POST, memasukkan URL endpoint lengkap dengan ID customer, lalu pada bagian Body memilih format raw JSON dan mengisi data booking sebelum mengirim request untuk mendapatkan response konfirmasi dari server.</p>
        </div>
        <div>
            <h3>POST /customers/{id}/bookings/{id}/reviews</h3>
            <img src="assets2/Post Customer_{id}_Bookings_{id}_Reviews.png" alt="Testing endpoint POST /customers/{id}/bookings/{id}/reviews dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint POST /customers/{id}/bookings/{id}/reviews yang berfungsi untuk menambahkan ulasan (review) dari seorang customer terhadap vila yang dipesan, berdasarkan informasi booking tertentu. Endpoint ini digunakan untuk menyimpan data review ke dalam database, seperti rating (bintang), judul, dan isi ulasan. Postman digunakan dengan membuat request baru, memilih method POST, memasukkan URL endpoint lengkap dengan ID customer dan ID booking, lalu pada bagian Body memilih format raw JSON dan mengisi data review sebelum mengirim request untuk mendapatkan response dari server.</p>
        </div>
        <div>
            <h3>PUT /customers/{id}</h3>
            <img src="assets2/Put customers_{id}.png" alt="Testing endpoint PUT /customers/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint PUT /customers/{id} yang berfungsi untuk mengubah atau memperbarui data seorang customer berdasarkan ID yang diberikan. Endpoint ini digunakan untuk memperbarui informasi customer seperti name, email, atau phone yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method PUT, memasukkan URL endpoint lengkap dengan ID customer, lalu pada bagian Body memilih format raw JSON dan mengisi data baru customer sebelum mengirim request untuk mendapatkan response dari server.</p>
        </div>
        <h2><strong>3. Voucher</strong></h2>
        <div>
            <h3>GET /vouchers</h3>
            <img src="assets2/Get vouchers.png" alt="Testing endpoint GET /vouchers dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /vouchers yang berfungsi untuk mengambil dan menampilkan daftar semua voucher yang tersedia dalam sistem. Endpoint ini digunakan untuk melihat semua data voucher yang tersimpan di database, lengkap dengan informasi seperti id, code, description, discount, dan periode berlaku. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint /vouchers, lalu mengirim request untuk mendapatkan response berupa daftar voucher dari server.</p>
        </div>
        <div>
            <h3>GET /vouchers/{id}</h3>
            <img src="assets2/Get vouchers_{id}.png" alt="Testing endpoint GET /vouchers/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint GET /vouchers/{id} yang berfungsi untuk mengambil dan menampilkan informasi detail dari sebuah voucher tertentu berdasarkan ID yang diberikan. Endpoint ini digunakan untuk melihat detail voucher seperti code, description, discount, serta periode berlakunya yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method GET, memasukkan URL endpoint lengkap dengan ID voucher, lalu mengirim request untuk mendapatkan response berisi detail voucher dari server.</p>
        </div>
        <div>
            <h3>POST /vouchers</h3>
            <img src="assets2/Post vouchers.png" alt="Testing endpoint POST /vouchers dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint POST /vouchers yang berfungsi untuk membuat dan menambahkan voucher baru ke dalam sistem. Endpoint ini digunakan untuk menyimpan data voucher baru, seperti code, description, discount, serta periode berlaku, ke dalam database. Postman digunakan dengan membuat request baru, memilih method POST, memasukkan URL endpoint /vouchers, lalu pada bagian Body memilih format raw JSON dan mengisi data voucher sebelum mengirim request untuk mendapatkan response konfirmasi dari server.</p>
        </div>
        <div>
            <h3>PUT /vouchers/{id}</h3>
            <img src="assets2/Put vouchers_{id}.png" alt="Testing endpoint PUT /vouchers/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint PUT /vouchers/{id} yang berfungsi untuk mengubah atau memperbarui data sebuah voucher tertentu berdasarkan ID yang diberikan. Endpoint ini digunakan untuk memperbarui informasi voucher seperti code, description, discount, atau periode berlakunya yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method PUT, memasukkan URL endpoint lengkap dengan ID voucher, lalu pada bagian Body memilih format raw JSON dan mengisi data baru voucher sebelum mengirim request untuk mendapatkan response dari server.</p>
        </div>
        <div>
            <h3>DELETE /vouchers/{id}</h3>
            <img src="assets2/Delete vouchers_{id}.png" alt="Testing endpoint DELETE /vouchers/{id} dengan Postman" width="600" />
            <p align="justify">Gambar tersebut menunjukkan penggunaan aplikasi Postman untuk melakukan testing API endpoint DELETE /vouchers/{id} yang berfungsi untuk menghapus data sebuah voucher tertentu dari sistem berdasarkan ID yang diberikan. Endpoint ini digunakan untuk menghapus informasi voucher yang tersimpan di database. Postman digunakan dengan membuat request baru, memilih method DELETE, memasukkan URL endpoint lengkap dengan ID voucher, lalu mengirim request untuk mendapatkan response konfirmasi dari server.</p>
        </div>
    </div>
</body>
</html>
