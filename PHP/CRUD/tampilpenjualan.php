<?php
require_once 'connection.php';

$query = "SELECT * FROM penjualan";

$result = mysqli_query($con, $query);

if (!$result) {
    die('SQL Error: ' . mysqli_error($con));
}

echo "<h1 style='text-align:center';>LAPORAN PENJUALAN<h1>";

echo "
        <table align='center' style=text-align:center;width:100% border='1px solid black'>
        <thead class='thead-dark'>
        <tr>
        <th style=background:#E5E7E9 scope='col' class = 'table-header' >KODE</th>
        <th style=background:#E5E7E9 scope='col'>Nama Barang</th>
        <th style=background:#E5E7E9 scope='col'>Satuan</th>
        <th style=background:#E5E7E9 scope='col'>Harga</th>
        <th style=background:#E5E7E9 scope='col'>Jumlah</th>
        <th style=background:#E5E7E9 scope='col'>Terjual</th>
        </tr>
        </thead>
        <tbody>";

while ($row = mysqli_fetch_array($result)) {
    echo '
    <tr>
        <td>' . $row['kode'] . '</td>
        <td>' . $row['nama_barang'] . '</td>
        <td>' . $row['satuan'] . '</td>
        <td>' . "Rp ." . number_format($row['harga'], 0, ',', '.') . '</td>
        <td>' . $row['jumlah'] . '</td>
        <td>' . $row['terjual'] . '</td>
    </tr>';
}
echo '
	</tbody>
</table>';

// Apakah kita perlu menjalankan fungsi mysqli_free_result() ini? baca bagian VII
mysqli_free_result($result);

// Apakah kita perlu menjalankan fungsi mysqli_close() ini? baca bagian VII
mysqli_close($con);
