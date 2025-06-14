document.addEventListener("DOMContentLoaded", () => {
  const API_BASE_URL = "http://localhost:8080/api";
  const ADMIN_API_URL = `${API_BASE_URL}/admin`;

  // Elemen UI Admin
  const mahasiswaTableBody = document.getElementById("mahasiswa-table-body");
  const mkTableBody = document.getElementById("mk-table-body");
  const addMahasiswaBtn = document.getElementById("addMahasiswaBtn");
  const addMkBtn = document.getElementById("addMkBtn");

  // Elemen UI Modal
  const formModal = document.getElementById("formModal");
  const modalTitle = document.getElementById("modalTitle");
  const modalFormFields = document.getElementById("modal-form-fields");
  const modalForm = document.getElementById("modalForm");
  const closeModalBtn = document.getElementById("closeModalBtn");
  const cancelModalBtn = document.getElementById("cancelModalBtn");

  let currentEditId = null;
  let currentEntityType = "";

  // --- FUNGSI MODAL ---
  const openModal = (title, fields, entityType, data = {}) => {
    modalTitle.textContent = title;
    modalFormFields.innerHTML = fields;
    currentEntityType = entityType;
    currentEditId = data.id || null;

    if (currentEditId) {
      for (const key in data) {
        const input = modalForm.querySelector(`[name="${key}"]`);
        if (input) input.value = data[key];
      }
    }

    formModal.classList.remove("hidden");
    setTimeout(() => {
      formModal.classList.remove("opacity-0");
      formModal.querySelector(".modal-container").classList.remove("scale-95");
    }, 10);
  };

  const closeModal = () => {
    formModal.classList.add("opacity-0");
    formModal.querySelector(".modal-container").classList.add("scale-95");
    setTimeout(() => {
      formModal.classList.add("hidden");
      modalForm.reset();
    }, 300);
  };

  closeModalBtn.addEventListener("click", closeModal);
  cancelModalBtn.addEventListener("click", closeModal);

  // --- FUNGSI RENDER & FETCH DATA ADMIN ---
  const renderMahasiswaTable = (mahasiswas) => {
    mahasiswaTableBody.innerHTML = "";
    if (mahasiswas.length === 0) {
      mahasiswaTableBody.innerHTML = `<tr><td colspan="3" class="text-center p-4 text-gray-500">Data tidak ditemukan.</td></tr>`;
      return;
    }
    mahasiswas.forEach((m) => {
      const row = document.createElement("tr");
      row.className = "bg-gray-800 border-b border-gray-700 hover:bg-gray-600";
      row.innerHTML = `
              <td class="px-6 py-4 font-medium text-gray-200 whitespace-nowrap">${m.npm}</td>
              <td class="px-6 py-4">${m.nama}</td>
              <td class="px-6 py-4 text-right">
                  <button class="font-medium text-indigo-400 hover:underline mr-3 edit-btn" data-type="mahasiswa" data-id="${m.id}">Ubah</button>
                  <button class="font-medium text-red-500 hover:underline delete-btn" data-type="mahasiswa" data-id="${m.id}">Hapus</button>
              </td>
          `;
      mahasiswaTableBody.appendChild(row);
    });
  };

  const renderMkTable = (mks) => {
    if (!mkTableBody) {
      console.error('Element with id "mk-table-body" not found.');
      return;
    }
    mkTableBody.innerHTML = "";
    if (!Array.isArray(mks) || mks.length === 0) {
      mkTableBody.innerHTML = `<tr><td colspan="4" class="text-center p-4 text-gray-500">Data tidak ditemukan.</td></tr>`;
      return;
    }
    mks.forEach((mk) => {
      const row = document.createElement("tr");
      row.className = "bg-gray-800 border-b border-gray-700 hover:bg-gray-600";
      row.innerHTML = `
              <td class="px-6 py-4 font-medium text-gray-200 whitespace-nowrap">${mk.kodeMk}</td>
              <td class="px-6 py-4">${mk.namaMk}</td>
              <td class="px-6 py-4">${mk.sks}</td>
              <td class="px-6 py-4 text-right">
                  <button class="font-medium text-indigo-400 hover:underline mr-3 edit-btn" data-type="matakuliah" data-id="${mk.id}">Ubah</button>
                  <button class="font-medium text-red-500 hover:underline delete-btn" data-type="matakuliah" data-id="${mk.id}">Hapus</button>
              </td>
          `;
      mkTableBody.appendChild(row);
    });
  };

  const fetchAdminData = async () => {
    try {
      const [mahasiswaRes, mkRes] = await Promise.all([
        fetch(`${ADMIN_API_URL}/mahasiswa`),
        fetch(`${ADMIN_API_URL}/matakuliah`),
      ]);
      const mahasiswas = await mahasiswaRes.json();
      const mks = await mkRes.json();

      renderMahasiswaTable(mahasiswas);
      renderMkTable(mks);
    } catch (error) {
      console.error("Gagal mengambil data admin:", error);
      mahasiswaTableBody.innerHTML = `<tr><td colspan="3" class="text-center text-red-400 p-4">Gagal memuat data.</td></tr>`;
      mkTableBody.innerHTML = `<tr><td colspan="4" class="text-center text-red-400 p-4">Gagal memuat data.</td></tr>`;
    }
  };

  // --- EVENT LISTENERS ADMIN ---
  addMahasiswaBtn.addEventListener("click", () => {
    const fields = `
          <div class="mb-4">
              <label for="npm" class="block text-sm font-medium text-gray-300 mb-1">NPM</label>
              <input type="text" name="npm" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600 focus:ring-indigo-500 focus:border-indigo-500" required>
          </div>
          <div class="mb-4">
              <label for="nama" class="block text-sm font-medium text-gray-300 mb-1">Nama Mahasiswa</label>
              <input type="text" name="nama" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600 focus:ring-indigo-500 focus:border-indigo-500" required>
          </div>
      `;
    openModal("Tambah Mahasiswa Baru", fields, "mahasiswa");
  });

  addMkBtn.addEventListener("click", () => {
    const fields = `
        <div class="mb-4">
          <label for="kodeMk" class="block text-sm font-medium text-gray-300 mb-1">Kode MK</label>
          <input type="text" name="kodeMk" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600" required>
        </div>
        <div class="mb-4">
          <label for="namaMk" class="block text-sm font-medium text-gray-300 mb-1">Nama Mata Kuliah</label>
          <input type="text" name="namaMk" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600" required>
        </div>
         <div class="mb-4">
          <label for="sks" class="block text-sm font-medium text-gray-300 mb-1">SKS</label>
          <input type="number" name="sks" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600" required min="1" max="4">
        </div>
      `;
    openModal("Tambah Mata Kuliah Baru", fields, "matakuliah");
  });

  document
    .getElementById("admin-section")
    .addEventListener("click", async (e) => {
      const target = e.target;
      if (target.classList.contains("edit-btn")) {
        const type = target.dataset.type;
        const id = target.dataset.id;
        const row = target.closest("tr");

        if (type === "mahasiswa") {
          const data = {
            id,
            npm: row.cells[0].textContent,
            nama: row.cells[1].textContent,
          };
          const fields = `
                  <div class="mb-4"><label for="npm" class="block text-sm font-medium text-gray-300 mb-1">NPM</label><input type="text" name="npm" value="${data.npm}" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600"></div>
                  <div class="mb-4"><label for="nama" class="block text-sm font-medium text-gray-300 mb-1">Nama</label><input type="text" name="nama" value="${data.nama}" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600"></div>
              `;
          openModal("Ubah Data Mahasiswa", fields, "mahasiswa", data);
        } else if (type === "matakuliah") {
          const data = {
            id,
            kodeMk: row.cells[0].textContent,
            namaMk: row.cells[1].textContent,
            sks: row.cells[2].textContent,
          };
          const fields = `
                  <div class="mb-4"><label for="kodeMk" class="block text-sm font-medium text-gray-300 mb-1">Kode MK</label><input type="text" name="kodeMk" value="${data.kodeMk}" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600"></div>
                  <div class="mb-4"><label for="namaMk" class="block text-sm font-medium text-gray-300 mb-1">Nama MK</label><input type="text" name="namaMk" value="${data.namaMk}" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600"></div>
                  <div class="mb-4"><label for="sks" class="block text-sm font-medium text-gray-300 mb-1">SKS</label><input type="number" name="sks" value="${data.sks}" class="w-full bg-gray-700 text-white rounded-md p-2 border border-gray-600" min="1"></div>
              `;
          openModal("Ubah Data Mata Kuliah", fields, "matakuliah", data);
        }
      }

      if (target.classList.contains("delete-btn")) {
        const type = target.dataset.type;
        const id = target.dataset.id;
        if (confirm(`Apakah Anda yakin ingin menghapus data ini?`)) {
          try {
            const response = await fetch(`${ADMIN_API_URL}/${type}/${id}`, {
              method: "DELETE",
            });
            if (!response.ok) {
              const responseError = await response.json();
              throw new Error(
                responseError.message || JSON.stringify(responseError)
              );
            }
            await fetchAdminData();
          } catch (error) {
            alert(error.message);
          }
        }
      }
    });

  modalForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const formData = new FormData(modalForm);
    const data = Object.fromEntries(formData.entries());

    if (data.sks) data.sks = parseInt(data.sks);

    const url = currentEditId
      ? `${ADMIN_API_URL}/${currentEntityType}/${currentEditId}`
      : `${ADMIN_API_URL}/${currentEntityType}`;

    const method = currentEditId ? "PUT" : "POST";

    try {
      const response = await fetch(url, {
        method: method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        const responseError = await response.json();
        throw new Error(responseError.message || JSON.stringify(responseError));
      }

      closeModal();
      await fetchAdminData();
    } catch (error) {
      alert(error.message);
    }
  });

  const initializeApp = () => {
    fetchAdminData();
  };

  initializeApp();
});
