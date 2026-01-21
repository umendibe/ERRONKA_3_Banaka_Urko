const produktuak = [
    // OCTAVIA Familia
    { id: 1, izena: "Skoda Octavia", eredua: "Octavia", irudia: "img/kotxeak/octavia.png", kategoria: "octavia" },
    { id: 2, izena: "Octavia Combi", eredua: "Octavia Combi", irudia: "img/kotxeak/octavia_combi.png", kategoria: "octavia" },
    { id: 3, izena: "Octavia RS", eredua: "RS", irudia: "img/kotxeak/octavia_rs.png", kategoria: "octavia" },
    { id: 4, izena: "Octavia Combi RS", eredua: "Combi RS", irudia: "img/kotxeak/octavia_combi_rs.png", kategoria: "octavia" },
    { id: 5, izena: "Octavia Sportline", eredua: "Sportline", irudia: "img/kotxeak/octavia_sportline.png", kategoria: "octavia" },
    { id: 6, izena: "Octavia Combi Sportline", eredua: "Combi Sportline", irudia: "img/kotxeak/octavia_combi_sportline.png", kategoria: "octavia" },

    // SUPERB Familia
    { id: 7, izena: "Skoda Superb", eredua: "Superb", irudia: "img/kotxeak/superb.png", kategoria: "superb" },
    { id: 8, izena: "Superb iV", eredua: "PHEV", irudia: "img/kotxeak/superb_iv.png", kategoria: "superb" },
    { id: 9, izena: "Superb L&K", eredua: "Laurin & Klement", irudia: "img/kotxeak/superb_lk.png", kategoria: "superb" },
    { id: 10, izena: "Superb Sportline", eredua: "Sportline", irudia: "img/kotxeak/superb_sportline.png", kategoria: "superb" },

    // ENYAQ Familia
    { id: 11, izena: "Skoda Enyaq", eredua: "Electric", irudia: "img/kotxeak/enyaq.png", kategoria: "enyaq" },
    { id: 12, izena: "Enyaq RS", eredua: "RS Electric", irudia: "img/kotxeak/enyaq_rs.png", kategoria: "enyaq" },
    { id: 13, izena: "Enyaq Coupé RS", eredua: "Coupé RS", irudia: "img/kotxeak/enyaq_coupe_rs.png", kategoria: "enyaq" },
    { id: 14, izena: "Enyaq Coupé Sportline", eredua: "Sportline", irudia: "img/kotxeak/enyaq_coupe_sportline.png", kategoria: "enyaq" },

    // KODIAQ Familia
    { id: 15, izena: "Skoda Kodiaq", eredua: "Kodiaq", irudia: "img/kotxeak/kadiaq.png", kategoria: "kodiaq" },
    { id: 16, izena: "Kodiaq RS", eredua: "RS", irudia: "img/kotxeak/kodiaq_rs.png", kategoria: "kodiaq" },

    // ELROQ Familia
    { id: 17, izena: "Skoda Elroq", eredua: "Elroq", irudia: "img/kotxeak/elroq.png", kategoria: "elroq" },
    { id: 18, izena: "Elroq RS", eredua: "RS", irudia: "img/kotxeak/elroq_rs.png", kategoria: "elroq" },
    { id: 19, izena: "Elroq Sportline", eredua: "Sportline", irudia: "img/kotxeak/elroq_sportline.png", kategoria: "elroq" },

    // BESTEAK
    { id: 20, izena: "Skoda Fabia", eredua: "Fabia", irudia: "img/kotxeak/fabia.png", kategoria: "besteak" },
    { id: 21, izena: "Skoda Kamiq", eredua: "Kamiq", irudia: "img/kotxeak/kamiq.png", kategoria: "besteak" },
    { id: 22, izena: "Skoda Karoq", eredua: "Karoq", irudia: "img/kotxeak/karoq.png", kategoria: "besteak" },
    { id: 23, izena: "Skoda Scala", eredua: "Scala", irudia: "img/kotxeak/scala.png", kategoria: "besteak" }
];

document.addEventListener("DOMContentLoaded", () => {
    const produktuSarea = document.getElementById("produktu-sarea");
    const iragazkiBotoiak = document.querySelectorAll(".iragazki-botoia");

    function bistaratuProduktuak(kategoria = "guztiak") {
        produktuSarea.innerHTML = "";

        const iragazitakoProduktuak = kategoria === "guztiak"
            ? produktuak
            : produktuak.filter(p => p.kategoria === kategoria);

        iragazitakoProduktuak.forEach(produktua => {
            const txartela = document.createElement("div");
            txartela.classList.add("produktu-txartela");

            txartela.innerHTML = `
                <img src="${produktua.irudia}" alt="${produktua.izena}" class="produktu-irudia">
                <div class="produktu-informazioa">
                    <h3 class="produktu-titulua">${produktua.izena}</h3>
                    <p class="produktu-eredua">${produktua.eredua}</p>
                </div>
            `;
            produktuSarea.appendChild(txartela);
        });
    }

    // Iragazkietarako gertaera entzuleak
    iragazkiBotoiak.forEach(botoia => {
        botoia.addEventListener("click", () => {
            // Botoi guztietatik aktibo klasea kendu
            iragazkiBotoiak.forEach(b => b.classList.remove("aktibo"));
            // Klik egindako botoiari aktibo klasea gehitu
            botoia.classList.add("aktibo");

            const iragazkiBalioa = botoia.getAttribute("data-iragazkia");
            bistaratuProduktuak(iragazkiBalioa);
        });
    });

    // Hasierako bistaratzea
    bistaratuProduktuak();
});
