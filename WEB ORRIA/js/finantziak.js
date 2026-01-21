/* Interes tasak (%) */
const interesEhunekoak = {
    arrunta: 6,   // 6%
    premium: 4    // 4%
};

/* Hilabeteak */
const planHilabeteak = {
    arrunta: 12,
    premium: 24
};

/* ==========================
   HASIERA
========================== */
document.addEventListener('DOMContentLoaded', () => {
    const kotxePrezioa = document.getElementById('kotxe-prezioa');
    const hilabeteBotoiak = document.querySelectorAll('.hilabete-botoia');
    const hautatuBotoiak = document.querySelectorAll('.hautatu-botoia');

    /* Prezioa aldatzean */
    kotxePrezioa.addEventListener('input', () => {
        kalkulatuDena();
    });

    /* Hilabeteak aldatzean */
    hilabeteBotoiak.forEach(botoia => {
        botoia.addEventListener('click', (e) => {
            const plan = e.target.getAttribute('data-plan');
            const hilabeteak = parseInt(e.target.getAttribute('data-hilabeteak'));

            /* Aktibo klasea */
            document.querySelectorAll(`.hilabete-botoia[data-plan="${plan}"]`).forEach(b => {
                b.classList.remove('aktibo');
            });
            e.target.classList.add('aktibo');

            planHilabeteak[plan] = hilabeteak;
            kalkulatuDena();
        });
    });

    /* Kalkulu nagusia */
    function kalkulatuDena() {
        const prezioa = parseFloat(kotxePrezioa.value);

        kalkulatuPlana('arrunta', prezioa);
        kalkulatuPlana('premium', prezioa);
    }

    /* Plan bat kalkulatu */
    function kalkulatuPlana(planMota, prezioa) {
        const hilabeteak = planHilabeteak[planMota];
        const interesEhunekoa = interesEhunekoak[planMota];

        /* INTERES SINPLEA */
        const urteak = hilabeteak / 12;
        const interesak = (prezioa * interesEhunekoa * urteak) / 100;

        /* Guztira */
        const guztira = prezioa + interesak;

        /* Hileko kuota */
        const kuota = guztira / hilabeteak;

        /* Erakutsi */
        erakutsiEmaitzak(planMota, prezioa, hilabeteak, interesak, kuota, guztira);
    }

    /* Emaitzak erakutsi */
    function erakutsiEmaitzak(plan, prezioa, hilabeteak, interesak, kuota, guztira) {
        if (plan === 'arrunta') {
            document.getElementById('finantzatua-arrunta').textContent = formatu(prezioa) + '€';
            document.getElementById('hilabeteak-arrunta').textContent = hilabeteak + ' hilabete';
            document.getElementById('interesak-arrunta').textContent = formatu(interesak) + '€';
            document.getElementById('kuota-arrunta').textContent = formatu(kuota) + '€/hilean';
            document.getElementById('guztira-arrunta').textContent = formatu(guztira) + '€';
        } else {
            document.getElementById('finantzatua-premium').textContent = formatu(prezioa) + '€';
            document.getElementById('hilabeteak-premium').textContent = hilabeteak + ' hilabete';
            document.getElementById('interesak-premium').textContent = formatu(interesak) + '€';
            document.getElementById('kuota-premium').textContent = formatu(kuota) + '€/hilean';
            document.getElementById('guztira-premium').textContent = formatu(guztira) + '€';
        }
    }

    /* Zenbakia formatatu */
    function formatu(zenbakia) {
        return Math.round(zenbakia).toLocaleString('eu-ES');
    }

    /* Plana hautatu */
    hautatuBotoiak.forEach(botoia => {
        botoia.addEventListener('click', (e) => {
            const plan = e.target.getAttribute('data-plan');
            const planIzena = plan === 'arrunta' ? 'Plan Arrunta' : 'Plan Premium';
            const kuota = plan === 'arrunta'
                ? document.getElementById('kuota-arrunta').textContent
                : document.getElementById('kuota-premium').textContent;

            alert('💰 ' + planIzena + '\n\nHileko kuota: ' + kuota + '\n\nEskerrik asko!');
        });
    });

    /* Hasierako kalkulua */
    kalkulatuDena();
});
