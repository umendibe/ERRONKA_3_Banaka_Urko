/* ==========================
   IRUDIEN MULTZOA
========================== */
/* Irudiak eta haien testuak objektuetan definituta daude */
const images = [
    { src: 'img/kotxeak/octavia.png', text: 'OCTAVIA' },
    { src: 'img/kotxeak/fabia.png', text: 'FABIA' },
    { src: 'img/kotxeak/kadiaq.png', text: 'KODIAQ' },
    { src: 'img/kotxeak/kamiq.png', text: 'KAMIQ' },
    { src: 'img/kotxeak/enyaq.png', text: 'ENYAQ' },
];

/* ==========================
   HASIERAKO ALDAGAIK
========================== */
/* Index aldagaiak zein irudi erakutsi behar den kontrolatzen du */
let index = 0;

/* HTML dokumentutik irudi elementua hartu */
const img = document.getElementById('hasierakoArgazkia');
const testuak = document.querySelector('.orriNagusi_testua');

/* ==========================
   IRUDIA EGUNERATZEKO FUNTZIOA
========================== */
/* Argazkia eta testua eguneratzen ditu */
function eguneratuArgazkia() {
    if (!img) return; // Elementua ez badago, ez egin ezer
    img.src = images[index].src; // Irudi berriaren bidea jarri
    testuak.textContent = images[index].text; // Testua eguneratu

    img.classList.add('fade-out'); // Fade efektua gehitu

    /* 200ms geroago irudi berria aplikatu eta efektua kendu */
    setTimeout(() => {
        img.src = images[index].src;
        img.classList.remove('fade-out');
    }, 200);
}

/* ==========================
   NABIGAZIO FUNTZIOAK
========================== */
/* Hurrengo irudia erakusten du */
function hurrengoIrudia() {
    if (index < images.length - 1) { // Azken irudira heldu gabe
        index++;
        eguneratuArgazkia();
    }
}

/* Aurreko irudia erakusten du */
function atzeraIrudia() {
    if (index > 0) { // Lehen irudira heldu gabe
        index--;
        eguneratuArgazkia();
    }
}

/* ==========================
   HASIERAKO ESKU-HARTZEA
========================== */
/* DOM guztia kargatu ondoren irudia lehenengo aldiz erakutsi */
document.addEventListener('DOMContentLoaded', eguneratuArgazkia);
