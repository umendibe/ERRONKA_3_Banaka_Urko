/* ==========================
   KOTXE KONFIGURATZAILEA
========================== */

/* Oinarrizko prezioak eredu bakoitzeko */
const oinarriPrezioak = {
    'octavia': 25000,
    'superb': 35000,
    'enyaq': 45000,
    'kodiaq': 38000,
    'fabia': 18000
};

/* Konfigurazio globala gordetzeko objektua */
const konfigurazioa = {
    eredua: null,
    oinarriPrezioa: 0,
    kolorea: null,
    kolorePrezioa: 0,
    motorea: null,
    motorPrezioa: 0,
    gehigarriak: []
};

/* ==========================
   DOM ELEMENTUAK
========================== */
document.addEventListener('DOMContentLoaded', () => {
    const ereduaHautatu = document.getElementById('eredua-hautatu');
    const koloreBotoak = document.querySelectorAll('.kolore-botoia');
    const koloreTestua = document.getElementById('kolore-testua');
    const motorRadioak = document.querySelectorAll('input[name="motorea"]');
    const gehigarriCheckboxak = document.querySelectorAll('.gehigarri-checkbox');
    const eskaeraBalidali = document.getElementById('eskaera-bidali');

    /* Laburpen elementuak */
    const laburpenEredua = document.getElementById('laburpen-eredua');
    const laburpenKolorea = document.getElementById('laburpen-kolorea');
    const laburpenMotorea = document.getElementById('laburpen-motorea');
    const laburpenGehigarriak = document.getElementById('laburpen-gehigarriak');
    const prezioGuztira = document.getElementById('prezio-guztira');

    /* ==========================
       EREDUA ALDATZEA
    ========================== */
    ereduaHautatu.addEventListener('change', (e) => {
        const hautatutakoEredua = e.target.value;

        if (hautatutakoEredua) {
            konfigurazioa.eredua = hautatutakoEredua;
            konfigurazioa.oinarriPrezioa = oinarriPrezioak[hautatutakoEredua];

            /* Ereduaren izena formatu */
            const ereduIzena = e.target.options[e.target.selectedIndex].text;
            laburpenEredua.textContent = ereduIzena;
        } else {
            konfigurazioa.eredua = null;
            konfigurazioa.oinarriPrezioa = 0;
            laburpenEredua.textContent = 'Hautatu eredua';
        }

        eguneratuPrezioa();
    });

    /* ==========================
       KOLOREA ALDATZEA
    ========================== */
    koloreBotoak.forEach(botoia => {
        botoia.addEventListener('click', () => {
            /* Kendu aktibo klasea beste guztietatik */
            koloreBotoak.forEach(b => b.classList.remove('hautatuta'));

            /* Gehitu aktibo klasea hautatutakoari */
            botoia.classList.add('hautatuta');

            const kolorea = botoia.getAttribute('data-kolorea');
            const prezioa = parseInt(botoia.getAttribute('data-prezioa'));

            konfigurazioa.kolorea = kolorea;
            konfigurazioa.kolorePrezioa = prezioa;

            /* Kolorearen izena letra larriz */
            const koloreIzena = kolorea.charAt(0).toUpperCase() + kolorea.slice(1);
            koloreTestua.textContent = koloreIzena;
            laburpenKolorea.textContent = koloreIzena + (prezioa > 0 ? ` (+${prezioa}€)` : '');

            eguneratuPrezioa();
        });
    });

    /* ==========================
       MOTOREA ALDATZEA
    ========================== */
    motorRadioak.forEach(radio => {
        radio.addEventListener('change', (e) => {
            const motorea = e.target.value;
            const prezioa = parseInt(e.target.getAttribute('data-prezioa'));

            konfigurazioa.motorea = motorea;
            konfigurazioa.motorPrezioa = prezioa;

            laburpenMotorea.textContent = motorea + (prezioa > 0 ? ` (+${prezioa.toLocaleString()}€)` : '');

            eguneratuPrezioa();
        });
    });

    /* ==========================
       GEHIGARRIAK ALDATZEA
    ========================== */
    gehigarriCheckboxak.forEach(checkbox => {
        checkbox.addEventListener('change', (e) => {
            const izena = e.target.getAttribute('data-izena');
            const prezioa = parseInt(e.target.getAttribute('data-prezioa'));

            if (e.target.checked) {
                /* Gehitu gehigarria */
                konfigurazioa.gehigarriak.push({ izena, prezioa });
            } else {
                /* Kendu gehigarria */
                konfigurazioa.gehigarriak = konfigurazioa.gehigarriak.filter(
                    g => g.izena !== izena
                );
            }

            eguneratuGehigarriak();
            eguneratuPrezioa();
        });
    });

    /* ==========================
       GEHIGARRIEN LABURPENA EGUNERATU
    ========================== */
    function eguneratuGehigarriak() {
        if (konfigurazioa.gehigarriak.length === 0) {
            laburpenGehigarriak.innerHTML = 'Ez dago gehigarririk';
        } else {
            const gehigarriZerrenda = konfigurazioa.gehigarriak
                .map(g => `<div>• ${g.izena} (+${g.prezioa}€)</div>`)
                .join('');
            laburpenGehigarriak.innerHTML = gehigarriZerrenda;
        }
    }

    /* ==========================
       PREZIO OSOA KALKULATU
    ========================== */
    function eguneratuPrezioa() {
        let prezioOsoa = konfigurazioa.oinarriPrezioa;
        prezioOsoa += konfigurazioa.kolorePrezioa;
        prezioOsoa += konfigurazioa.motorPrezioa;

        /* Gehitu gehigarrien prezioak */
        konfigurazioa.gehigarriak.forEach(g => {
            prezioOsoa += g.prezioa;
        });

        /* Erakutsi prezioa formatuarekin */
        prezioGuztira.textContent = prezioOsoa.toLocaleString('eu-ES') + '€';
    }

    /* ==========================
       ESKAERA BIDALI
    ========================== */
    eskaeraBalidali.addEventListener('click', () => {
        /* Egiaztatu gutxieneko datu beharrezkoak */
        if (!konfigurazioa.eredua) {
            alert('Mesedez, hautatu eredua.');
            return;
        }

        if (!konfigurazioa.kolorea) {
            alert('Mesedez, hautatu kolorea.');
            return;
        }

        if (!konfigurazioa.motorea) {
            alert('Mesedez, hautatu motorea.');
            return;
        }

        /* Sortu laburpen mezua */
        let mezua = '🚗 ESKAERA LABURPENA\n\n';
        mezua += `Eredua: ${laburpenEredua.textContent}\n`;
        mezua += `Kolorea: ${konfigurazioa.kolorea}\n`;
        mezua += `Motorea: ${konfigurazioa.motorea}\n`;

        if (konfigurazioa.gehigarriak.length > 0) {
            mezua += '\nGehigarriak:\n';
            konfigurazioa.gehigarriak.forEach(g => {
                mezua += `  • ${g.izena}\n`;
            });
        }

        mezua += `\nPREZIO OSOA: ${prezioGuztira.textContent}`;

        alert(mezua + '\n\nEskerrik asko zure eskaeragatik! Laster jarriko gara zurekin harremanetan.');

        /* Aukerakoa: berrezarri formularioa */
        // berrezarriKonfigurazioa();
    });

    /* ==========================
       KONFIGURAZIOA BERREZARRI
    ========================== */
    function berrezarriKonfigurazioa() {
        /* Berrezarri konfigurazio objektua */
        konfigurazioa.eredua = null;
        konfigurazioa.oinarriPrezioa = 0;
        konfigurazioa.kolorea = null;
        konfigurazioa.kolorePrezioa = 0;
        konfigurazioa.motorea = null;
        konfigurazioa.motorPrezioa = 0;
        konfigurazioa.gehigarriak = [];

        /* Berrezarri formulario elementuak */
        ereduaHautatu.value = '';
        koloreBotoak.forEach(b => b.classList.remove('hautatuta'));
        motorRadioak.forEach(r => r.checked = false);
        gehigarriCheckboxak.forEach(c => c.checked = false);

        /* Berrezarri laburpen testua */
        laburpenEredua.textContent = 'Hautatu eredua';
        laburpenKolorea.textContent = 'Hautatu kolorea';
        laburpenMotorea.textContent = 'Hautatu motorea';
        koloreTestua.textContent = 'Hautatu kolorea';
        eguneratuGehigarriak();
        eguneratuPrezioa();
    }
});
