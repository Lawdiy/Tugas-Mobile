//2410817310012 Faisal Tanjung,
data class DataClass(val key: String, val value: String)

class Account(idInput: String, nicknameInput: String?) {

    val id: String = idInput
        get() {
            return field.uppercase()
        }

    var nickName: String? = null
        set(value) {
            if (value == "") {
                field = null
            } else {
                field = value
            }
        }

    init {
        this.nickName = nicknameInput
    }
}


fun mainMenu() {
    println("Selamat Datang di Program CRUD Faisal!")
    println("Disini kalian bisa mencatat Akun Mobile Legend\ndengan memasukkan Id Akun dan nickname (opsional)")
    println("Silakan pilih Menu:\n1. Create Data\n2. Read Data\n3. Update Data\n4. Delete Data\n5. Show Data\n0. Exit Program")
    print("Pilihan: ")
}


fun createData(listAkun: ArrayList<Account>) {
    print("Masukkan Id Akun: ")
    val idBaru = readln()

    if (idBaru == "") {
        print("Id akun tidak boleh kosong! Kembali ke menu...")
        readln()
        return
    }

    var idAda = false
    for (akun in listAkun) {
        if (akun.id == idBaru.uppercase()) {
            idAda = true
        }
    }

    if (idAda == true) {
        print("ID '$idBaru' sudah dipakai! Kembali ke menu...")
        readln()
    } else {
        print("Masukkan Nickname: ")
        val nickBaru = readln()

        val akunBaru = Account(idBaru, nickBaru)
        listAkun.add(akunBaru)
        print("Berhasil mencatat akun! kembali ke menu...")
        readln()
    }
}

fun readData(listAkun: ArrayList<Account>) {
    if (listAkun.size == 0) {
        print("Data masih kosong. Kembali ke menu...")
    } else {
        println("\nDaftar Akun: ")
        for (akun in listAkun) {
            val namaTampil = akun.nickName ?: "Pemain Tidak ada Nickname"
            println("ID: ${akun.id} | Nickname: $namaTampil")
        }
    }
    readln()
}


fun updateData(listAkun: ArrayList<Account>) {
    print("Masukkan Id Akun yang mau diubah Nicknamenya: ")
    val idCari = readln().uppercase()

    var akunKetemu: Account? = null

    for (akun in listAkun) {
        if (akun.id == idCari) {
            akunKetemu = akun
        }
    }

    if (akunKetemu != null) {
        print("Masukkan Nickname yang baru: ")
        val nickBaru = readln()

        akunKetemu.nickName = nickBaru
        print("Berhasil update data! Kembali ke menu...")
        readln()
    } else {
        print("ID tidak ditemukan! kembali ke menu...")
        readln()
    }
}

fun deleteData(listAkun: ArrayList<Account>) {
    print("Masukkan Id Akun yang mau dihapus: ")
    val idHapus = readln().uppercase()

    var nomorAntrian = -1

    for (i in 0 until listAkun.size) {
        if (listAkun[i].id == idHapus) {
            nomorAntrian = i
        }
    }

    if (nomorAntrian != -1) {
        listAkun.removeAt(nomorAntrian)
        print("Berhasil menghapus data! Kembali ke menu...")
        readln()
    } else {
        print("Id akun tidak ditemukan! Kembali ke menu...")
        readln()
    }
}

fun showData(listAkun: ArrayList<Account>) {
    if (listAkun.size == 0) {
        print("Data masih kosong. Kembali ke menu...")
        readln()
    } else {
        println("\n---Show Data (Key, Value)---")
        for (akun in listAkun) {
            val namaTampil = akun.nickName ?: "NULL"

            val formatData = DataClass(key = akun.id, value = namaTampil)
            println("Key: ${formatData.key} --> Value: ${formatData.value}")
        }
    }
    readln()
}

fun main() {
    val listAkun = ArrayList<Account>()
    var running = true

    while (running == true) {
        mainMenu()
        when (readln()) {
            "1" -> createData(listAkun)
            "2" -> readData(listAkun)
            "3" -> updateData(listAkun)
            "4" -> deleteData(listAkun)
            "5" -> showData(listAkun)
            "0" -> {
                print("Terima kasih telah menggunakan program ini! Program akan ditutup...")
                readln()
                running = false
            }

            else -> println("Pilihan tidak valid, coba ketik angka 0-5!")
        }
    }
}