
USE Swalayan;

SET IDENTITY_INSERT Kasir ON;
INSERT INTO Kasir (id_kasir, nama_kasir, username, password, no_hp) 
VALUES (1, 'Budi Santoso', 'kasir_budi', '$2a$12$Zm.K6txbNH2JSFiqbPoxROP0ZlNy5o0OpRuqbbJ/GUSGu/6Dyyfwq', '081234567890');
SET IDENTITY_INSERT Kasir OFF;
GO

SET IDENTITY_INSERT PenjagaGudang ON;
INSERT INTO PenjagaGudang (id_gudang, nama_gudang, username, password) 
VALUES (1, 'Citra Lestari', 'gudang_citra', '$2a$12$LwW8UmEyour.jssNHIaEWu40OcSCYklt3UJBVFM6a9Gmmkntcv1bm');
SET IDENTITY_INSERT PenjagaGudang OFF;
GO

SET IDENTITY_INSERT Supervisor ON;
INSERT INTO Supervisor (id_supervisor, nama_supervisor, username, password) 
VALUES (1, 'Doni Firmansyah', 'spv_doni', '$2a$12$hgPxz6skZsFcZLlK9MnvZ.iGB2tR1K6fBHLSzcSNDQwIEky3UjAnO');
SET IDENTITY_INSERT Supervisor OFF;
GO

SET IDENTITY_INSERT Owner ON;
INSERT INTO Owner (id_owner, nama_owner, username, password) 
VALUES (1, 'Andi', 'owner_andi', '$2a$12$kcY7f8QWXuMqUvkByEYyzuJOse5VgAv3E4VbgwEnT2Dm.KtW4Q42.');
SET IDENTITY_INSERT Owner OFF;
GO


SET IDENTITY_INSERT Barang ON;
INSERT INTO Barang (id_barang, nama_barang, kategori, harga_beli, harga_jual, stok) 
VALUES (1, 'Indomie Goreng Special', 'Makanan Instan', 2800, 3200, 0);

INSERT INTO Barang (id_barang, nama_barang, kategori, harga_beli, harga_jual, stok) 
VALUES (2, 'Teh Pucuk Harum 350ml', 'Minuman', 3000, 3500, 0);

INSERT INTO Barang (id_barang, nama_barang, kategori, harga_beli, harga_jual, stok) 
VALUES (3, 'Silverqueen 65g', 'Cokelat', 10500, 12000, 0);
SET IDENTITY_INSERT Barang OFF;
GO


SET IDENTITY_INSERT TransaksiPembelian ON;
INSERT INTO TransaksiPembelian (id_pembelian, tanggal_pembelian, id_gudang, total_pembelian, supplier_name) 
VALUES (1, '2025-11-10 09:00:00', 1, 1485000, 'Supplier Utama');
SET IDENTITY_INSERT TransaksiPembelian OFF;


INSERT INTO DetailPembelian (id_pembelian, id_barang, jumlah_beli, harga_beli_satuan, subtotal_beli) 
VALUES (1, 1, 200, 2800, 560000); 
INSERT INTO DetailPembelian (id_pembelian, id_barang, jumlah_beli, harga_beli_satuan, subtotal_beli) 
VALUES (1, 2, 300, 3000, 900000); 
INSERT INTO DetailPembelian (id_pembelian, id_barang, jumlah_beli, harga_beli_satuan, subtotal_beli) 
VALUES (1, 3, 25, 10500, 262500); 

UPDATE Barang SET stok = stok + 200 WHERE id_barang = 1; 
UPDATE Barang SET stok = stok + 300 WHERE id_barang = 2; 
UPDATE Barang SET stok = stok + 25 WHERE id_barang = 3;  
GO



SET IDENTITY_INSERT TransaksiPenjualan ON;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (1, '2025-11-14 10:01:00', 6400, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (1, 1, 2, 6400);
UPDATE Barang SET stok = stok - 2 WHERE id_barang = 1;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (2, '2025-11-14 10:03:00', 3200, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (2, 1, 1, 3200);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 1;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (3, '2025-11-14 10:05:00', 9600, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (3, 1, 3, 9600);
UPDATE Barang SET stok = stok - 3 WHERE id_barang = 1;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (4, '2025-11-14 10:10:00', 3200, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (4, 1, 1, 3200);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 1;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (5, '2025-11-14 10:12:00', 6400, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (5, 1, 2, 6400);
UPDATE Barang SET stok = stok - 2 WHERE id_barang = 1;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (6, '2025-11-14 10:15:00', 7000, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (6, 2, 2, 7000);
UPDATE Barang SET stok = stok - 2 WHERE id_barang = 2;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (7, '2025-11-14 10:16:00', 3500, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (7, 2, 1, 3500);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 2;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (8, '2025-11-14 10:18:00', 3500, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (8, 2, 1, 3500);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 2;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (9, '2025-11-14 10:20:00', 10500, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (9, 2, 3, 10500);
UPDATE Barang SET stok = stok - 3 WHERE id_barang = 2;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (10, '2025-11-14 10:21:00', 7000, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (10, 2, 2, 7000);
UPDATE Barang SET stok = stok - 2 WHERE id_barang = 2;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (11, '2025-11-14 10:25:00', 3500, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (11, 2, 1, 3500);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 2;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (12, '2025-11-14 10:30:00', 3500, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (12, 2, 1, 3500);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 2;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (13, '2025-11-14 10:32:00', 12000, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (13, 3, 1, 12000);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 3;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (14, '2025-11-14 10:33:00', 24000, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (14, 3, 2, 24000);
UPDATE Barang SET stok = stok - 2 WHERE id_barang = 3;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (15, '2025-11-14 10:35:00', 12000, 1);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (15, 3, 1, 12000);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 3;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (16, '2025-11-14 10:40:00', 15200, 1); -- 1 Indomie, 1 Silverqueen
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (16, 1, 1, 3200);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (16, 3, 1, 12000);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 1;
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 3;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (17, '2025-11-14 10:45:00', 6700, 1); -- 1 Indomie, 1 Teh
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (17, 1, 1, 3200);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (17, 2, 1, 3500);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 1;
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 2;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (18, '2025-11-14 10:50:00', 15500, 1); -- 1 Teh, 1 Silverqueen
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (18, 2, 1, 3500);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (18, 3, 1, 12000);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 2;
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 3;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (19, '2025-11-14 10:55:00', 18700, 1); -- 1 Indomie, 1 Teh, 1 Silverqueen
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (19, 1, 1, 3200);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (19, 2, 1, 3500);
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (19, 3, 1, 12000);
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 1;
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 2;
UPDATE Barang SET stok = stok - 1 WHERE id_barang = 3;

INSERT INTO TransaksiPenjualan (id_penjualan, tanggal_penjualan, total_harga, id_kasir) VALUES (20, '2025-11-14 11:00:00', 32000, 1); -- 10 Indomie
INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (20, 1, 10, 32000);
UPDATE Barang SET stok = stok - 10 WHERE id_barang = 1;

SET IDENTITY_INSERT TransaksiPenjualan OFF;
GO

SET IDENTITY_INSERT StokOpname ON;
INSERT INTO StokOpname (id_opname, tanggal_opname, id_gudang, id_barang, stok_sistem, stok_fisik, status_verifikasi, id_supervisor, tanggal_verifikasi) 
VALUES (1, '2025-11-15 08:00:00', 1, 1, 178, 177, 'Verified', 1, '2025-11-15 09:00:00');
UPDATE Barang SET stok = 177 WHERE id_barang = 1;
GO

INSERT INTO StokOpname (id_opname, tanggal_opname, id_gudang, id_barang, stok_sistem, stok_fisik, status_verifikasi, id_supervisor, tanggal_verifikasi) 
VALUES (2, '2025-11-15 08:05:00', 1, 2, 289, 289, 'Pending', NULL, NULL); 
UPDATE StokOpname SET stok_sistem = 287, stok_fisik = 287 WHERE id_opname = 2;

SET IDENTITY_INSERT StokOpname OFF;
GO

SET IDENTITY_INSERT PrediksiStok ON;
INSERT INTO PrediksiStok (id_prediksi, id_barang, periode, hasil_prediksi, tanggal_dibuat) 
VALUES (1, 1, '2025-12', 300, '2025-11-15 10:00:00');

INSERT INTO PrediksiStok (id_prediksi, id_barang, periode, hasil_prediksi, tanggal_dibuat) 
VALUES (2, 2, '2025-12', 450, '2025-11-15 10:00:00'); 
SET IDENTITY_INSERT PrediksiStok OFF;
GO

PRINT 'DML Data berhasil di-eksekusi.';