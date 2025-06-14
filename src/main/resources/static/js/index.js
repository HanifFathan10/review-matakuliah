document.addEventListener("DOMContentLoaded", () => {
  const API_URL = "http://localhost:8080/api";

  const mataKuliahListEl = document.getElementById("mataKuliahList");
  const mataKuliahDetailEl = document.getElementById("mataKuliahDetail");
  const loadingEl = document.getElementById("loading");
  const backButton = document.getElementById("backButton");
  const reviewForm = document.getElementById("reviewForm");

  // --- Logika untuk Rating Bintang ---
  const ratingStarsContainer = document.getElementById("ratingStars");
  const ratingValueInput = document.getElementById("ratingValue");
  const stars = ratingStarsContainer.querySelectorAll("span");

  ratingStarsContainer.addEventListener("click", (e) => {
    if (e.target.tagName === "SPAN") {
      const value = e.target.dataset.value;
      ratingValueInput.value = value;
      updateStars(value);
    }
  });

  function updateStars(value) {
    stars.forEach((star) => {
      star.textContent = star.dataset.value <= value ? "★" : "☆";
      star.classList.toggle("text-yellow-400", star.dataset.value <= value);
      star.classList.toggle("text-gray-500", star.dataset.value > value);
    });
  }

  // --- Fungsi untuk render elemen ---
  const createMataKuliahCard = (mk) => {
    const card = document.createElement("div");
    card.className =
      "bg-gray-800 p-5 rounded-lg shadow-md hover:bg-gray-700 hover:shadow-xl transition-all duration-300 cursor-pointer";
    card.dataset.id = mk.id;
    card.innerHTML = `
                    <h3 class="font-bold text-lg text-teal-300">${mk.namaMk}</h3>
                    <p class="text-gray-400 text-sm">${mk.kodeMk} - ${mk.sks} SKS</p>
                `;
    card.addEventListener("click", () => fetchAndDisplayDetails(mk.id));
    return card;
  };

  const createReviewElement = (review) => {
    const reviewEl = document.createElement("div");
    reviewEl.className = "bg-gray-800/50 p-4 rounded-lg border border-gray-700";

    const ratingHtml =
      '<span class="text-yellow-400">★</span>'.repeat(review.rating) +
      '<span class="text-gray-500">☆</span>'.repeat(5 - review.rating);

    reviewEl.innerHTML = `
                    <div class="flex justify-between items-center mb-2">
                        <p class="font-semibold text-gray-200">${review.mahasiswaNama}</p>
                        <div class="flex items-center text-xs text-gray-400">${ratingHtml}</div>
                    </div>
                    <p class="text-gray-300 mb-2">${review.komentar}</p>
                    <p class="text-xs text-gray-500 text-right">${review.tanggalDibuat}</p>
                `;
    return reviewEl;
  };

  const createAverageRatingElement = (avgRating) => {
    const ratingInt = Math.round(avgRating);
    const ratingHtml =
      '<span class="text-yellow-400">★</span>'.repeat(ratingInt) +
      '<span class="text-gray-600">★</span>'.repeat(5 - ratingInt);
    return `${ratingHtml} <span class="ml-2 text-gray-400">${avgRating.toFixed(
      1
    )} dari 5</span>`;
  };

  // --- Fungsi Fetch API ---
  const fetchAndDisplayMataKuliah = async () => {
    loadingEl.style.display = "block";
    mataKuliahListEl.innerHTML = "";
    try {
      const response = await fetch(`${API_URL}/matakuliah`);
      if (!response.ok) throw new Error("Gagal mengambil data");
      const data = await response.json();
      data.forEach((mk) => {
        mataKuliahListEl.appendChild(createMataKuliahCard(mk));
      });
    } catch (error) {
      mataKuliahListEl.innerHTML = `<p class="text-red-400 text-center col-span-full">${error.message}. Pastikan backend sudah berjalan.</p>`;
    } finally {
      loadingEl.style.display = "none";
    }
  };

  const fetchAndDisplayDetails = async (id) => {
    mataKuliahListEl.classList.add("hidden");
    mataKuliahDetailEl.classList.remove("hidden");
    document.getElementById("reviewList").innerHTML =
      '<p class="text-gray-400">Memuat review...</p>';
    fetchDataMahasiswa();

    try {
      const response = await fetch(`${API_URL}/matakuliah/${id}`);
      if (!response.ok) throw new Error("Gagal memuat detail");
      const data = await response.json();

      // Isi detail MK
      document.getElementById("detailNamaMk").textContent = data.namaMk;
      document.getElementById("detailKodeMk").textContent = data.kodeMk;
      document.getElementById("detailSks").textContent = `${data.sks} SKS`;
      document.getElementById("detailAverageRating").innerHTML =
        createAverageRatingElement(data.averageRating);

      // Set ID pada form untuk submit
      reviewForm.dataset.mataKuliahId = id;

      // Tampilkan review
      const reviewListEl = document.getElementById("reviewList");
      reviewListEl.innerHTML = "";
      if (data.reviews.length > 0) {
        data.reviews.forEach((review) => {
          reviewListEl.appendChild(createReviewElement(review));
        });
      } else {
        reviewListEl.innerHTML =
          '<p class="text-gray-500 text-center italic">Belum ada review untuk mata kuliah ini.</p>';
      }
    } catch (error) {
      console.error("Error fetching details:", error);
      document.getElementById(
        "reviewList"
      ).innerHTML = `<p class="text-red-400">${error.message}</p>`;
    }
  };

  const fetchDataMahasiswa = async () => {
    try {
      const response = await fetch(`${API_URL}/admin/mahasiswa`);
      if (!response.ok) throw new Error("Gagal memuat detail");
      const data = await response.json();

      const mahasiswaListEl = document.getElementById("mahasiswaList");
      mahasiswaListEl.innerHTML = "";

      data.forEach((mhs) => {
        const option = document.createElement("option");
        option.value = mhs.id;
        option.textContent = mhs.nama;
        mahasiswaListEl.appendChild(option);
      });
    } catch (error) {
      console.log("🚀 ~ fetchDataMahasiswa ~ error:", error);
      document.getElementById(
        "mahasiswaList"
      ).innerHTML = `<p class="text-red-400">${error.message}</p>`;
    }
  };

  // --- Event Listeners ---
  backButton.addEventListener("click", () => {
    mataKuliahDetailEl.classList.add("hidden");
    mataKuliahListEl.classList.remove("hidden");
  });

  reviewForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const formData = new FormData(reviewForm);
    const mataKuliahId = reviewForm.dataset.mataKuliahId;
    const rating = parseInt(formData.get("rating"));
    const komentar = formData.get("komentar").trim();

    if (!rating || rating < 1 || rating > 5) {
      alert("Harap berikan rating bintang.");
      return;
    }
    if (komentar === "") {
      alert("Komentar tidak boleh kosong.");
      return;
    }

    const reviewData = {
      mahasiswaId: parseInt(formData.get("mahasiswaId")),
      rating: rating,
      komentar: komentar,
    };

    try {
      const response = await fetch(
        `${API_URL}/matakuliah/${mataKuliahId}/reviews`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(reviewData),
        }
      );

      if (!response.ok) {
        const responseError = await response.json();
        throw new Error(responseError.message || JSON.stringify(responseError));
      }

      const newReview = await response.json();

      const reviewListEl = document.getElementById("reviewList");
      const newReviewEl = createReviewElement(newReview);
      newReviewEl.classList.add("animate-fade-in");

      const noReviewMessage = reviewListEl.querySelector("p");
      if (noReviewMessage) noReviewMessage.remove();

      reviewListEl.prepend(newReviewEl);

      reviewForm.reset();
      updateStars(0);

      fetchAndDisplayDetails(mataKuliahId);
    } catch (error) {
      alert(error.message);
    }
  });

  fetchAndDisplayMataKuliah();
});
