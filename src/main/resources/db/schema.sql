
CREATE TABLE Kasir (
    id_kasir INT IDENTITY(1,1) PRIMARY KEY,
    nama_kasir VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    no_hp VARCHAR(20) NULL
);
GO


CREATE TABLE PenjagaGudang (
    id_gudang INT IDENTITY(1,1) PRIMARY KEY,
    nama_gudang VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
GO

CREATE TABLE Supervisor (
    id_supervisor INT IDENTITY(1,1) PRIMARY KEY,
    nama_supervisor VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
GO

CREATE TABLE Owner (
    id_owner INT IDENTITY(1,1) PRIMARY KEY,
    nama_owner VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
GO

CREATE TABLE Barang (
    id_barang INT IDENTITY(1,1) PRIMARY KEY,
    nama_barang VARCHAR(150) NOT NULL UNIQUE,
    kategori VARCHAR(50) NOT NULL,
    harga_beli MONEY NOT NULL,
    harga_jual MONEY NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    CONSTRAINT CHK_Barang_HargaBeli CHECK (harga_beli >= 0),
    CONSTRAINT CHK_Barang_HargaJual CHECK (harga_jual >= 0),
    CONSTRAINT CHK_Barang_Stok CHECK (stok >= 0)
);
GO

CREATE TABLE TransaksiPenjualan (
    id_penjualan INT IDENTITY(1,1) PRIMARY KEY,
    tanggal_penjualan DATETIME NOT NULL DEFAULT GETDATE(),
    total_harga MONEY NOT NULL DEFAULT 0,
    id_kasir INT NOT NULL,
    CONSTRAINT FK_Penjualan_Kasir FOREIGN KEY (id_kasir)
        REFERENCES Kasir(id_kasir)
        ON DELETE NO ACTION 
);
GO

CREATE TABLE DetailPenjualan (
    id_penjualan INT NOT NULL,
    id_barang INT NOT NULL,
    jumlah INT NOT NULL,
    subtotal MONEY NOT NULL,
    CONSTRAINT PK_DetailPenjualan PRIMARY KEY (id_penjualan, id_barang),
    CONSTRAINT FK_DetailPenjualan_Header FOREIGN KEY (id_penjualan)
        REFERENCES TransaksiPenjualan(id_penjualan)
        ON DELETE CASCADE, 
    CONSTRAINT FK_DetailPenjualan_Barang FOREIGN KEY (id_barang)
        REFERENCES Barang(id_barang)
        ON DELETE NO ACTION, 
    CONSTRAINT CHK_DetailPenjualan_Jumlah CHECK (jumlah > 0)
);
GO



CREATE TABLE TransaksiPembelian (
    id_pembelian INT IDENTITY(1,1) PRIMARY KEY,
    tanggal_pembelian DATETIME NOT NULL DEFAULT GETDATE(),
    id_gudang INT NOT NULL,
    total_pembelian MONEY NOT NULL DEFAULT 0,
    supplier_name VARCHAR(100) NULL,
    CONSTRAINT FK_Pembelian_Gudang FOREIGN KEY (id_gudang)
        REFERENCES PenjagaGudang(id_gudang)
        ON DELETE NO ACTION
);
GO


CREATE TABLE DetailPembelian (
    id_pembelian INT NOT NULL,
    id_barang INT NOT NULL,
    jumlah_beli INT NOT NULL,
    harga_beli_satuan MONEY NOT NULL,
    subtotal_beli MONEY NOT NULL,
    CONSTRAINT PK_DetailPembelian PRIMARY KEY (id_pembelian, id_barang),
    CONSTRAINT FK_DetailPembelian_Header FOREIGN KEY (id_pembelian)
        REFERENCES TransaksiPembelian(id_pembelian)
        ON DELETE CASCADE,
    CONSTRAINT FK_DetailPembelian_Barang FOREIGN KEY (id_barang)
        REFERENCES Barang(id_barang)
        ON DELETE NO ACTION,
    CONSTRAINT CHK_DetailPembelian_Jumlah CHECK (jumlah_beli > 0)
);
GO

CREATE TABLE StokOpname (
    id_opname INT IDENTITY(1,1) PRIMARY KEY,
    tanggal_opname DATETIME NOT NULL DEFAULT GETDATE(),
    id_gudang INT NOT NULL,
    id_barang INT NOT NULL,
    stok_sistem INT NOT NULL,
    stok_fisik INT NOT NULL,
    selisih AS (stok_fisik - stok_sistem) PERSISTED,

    status_verifikasi VARCHAR(20) NOT NULL DEFAULT 'Pending',
    id_supervisor INT NULL, 
    tanggal_verifikasi DATETIME NULL,
    
    CONSTRAINT FK_Opname_Gudang FOREIGN KEY (id_gudang)
        REFERENCES PenjagaGudang(id_gudang)
        ON DELETE NO ACTION,
    CONSTRAINT FK_Opname_Barang FOREIGN KEY (id_barang)
        REFERENCES Barang(id_barang)
        ON DELETE NO ACTION,
    CONSTRAINT FK_Opname_Supervisor FOREIGN KEY (id_supervisor)
        REFERENCES Supervisor(id_supervisor)
        ON DELETE NO ACTION,
    CONSTRAINT CHK_StokOpname_Status CHECK (status_verifikasi IN ('Pending', 'Verified', 'Rejected'))
);
GO

CREATE TABLE PrediksiStok (
    id_prediksi INT IDENTITY(1,1) PRIMARY KEY,
    id_barang INT NOT NULL,
    periode VARCHAR(7) NOT NULL, 
    hasil_prediksi INT NOT NULL,
    tanggal_dibuat DATETIME NOT NULL DEFAULT GETDATE(),
    
    CONSTRAINT FK_Prediksi_Barang FOREIGN KEY (id_barang)
        REFERENCES Barang(id_barang)
        ON DELETE CASCADE, 
    CONSTRAINT UQ_Prediksi_BarangPeriode UNIQUE (id_barang, periode)
);
GO
