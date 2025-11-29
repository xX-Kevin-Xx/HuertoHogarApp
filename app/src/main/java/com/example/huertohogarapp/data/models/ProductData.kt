package com.example.huertohogarapp.data.models

import com.example.huertohogarapp.R

object ProductData {
    val sampleProducts = listOf(
        Product(
            id = "FR001",
            name = "Manzanas Fuji",
            price = 1200.0,
            description = "Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule. Perfectas para meriendas saludables.",
            category = "Frutas Frescas",
            stock = 150,
            imageRes = R.drawable.manzana_fuji
        ),
        Product(
            id = "FR002",
            name = "Naranjas Valencia",
            price = 1000.0,
            description = "Jugosas y ricas en vitamina C, ideales para zumos frescos y refrescantes.",
            category = "Frutas Frescas",
            stock = 200,
            imageRes = R.drawable.naranja_valencia
        ),
        Product(
            id = "FR003",
            name = "Plátanos Cavendish",
            price = 800.0,
            description = "Plátanos maduros y dulces, perfectos para el desayuno o como snack energético.",
            category = "Frutas Frescas",
            stock = 250,
            imageRes = R.drawable.platanos_cavendish
        ),
        Product(
            id = "VR001",
            name = "Zanahorias Orgánicas",
            price = 900.0,
            description = "Zanahorias crujientes cultivadas sin pesticidas en la Región de O'Higgins.",
            category = "Verduras Orgánicas",
            stock = 100,
            imageRes = R.drawable.zanahorias
        ),
        Product(
            id = "VR002",
            name = "Espinacas Frescas",
            price = 700.0,
            description = "Espinacas frescas y nutritivas, perfectas para ensaladas y batidos verdes.",
            category = "Verduras Orgánicas",
            stock = 80,
            imageRes = R.drawable.espinacas_frescas
        ),
        Product(
            id = "PO001",
            name = "Miel Orgánica",
            price = 5000.0,
            description = "Miel pura y orgánica producida por apicultores locales.",
            category = "Productos Orgánicos",
            stock = 50,
            imageRes = R.drawable.miel
        ),
        Product(
            id = "PL001",
            name = "Leche Entera",
            price = 1200.0,
            description = "Leche entera fresca de vacas criadas en granjas locales.",
            category = "Productos Lácteos",
            stock = 75,
            imageRes = R.drawable.leche
        ),
        Product(
            id = "PO002",
            name = "Quinoa Orgánica",
            price = 4500.0,
            description = "Granos de quinoa orgánica, altos en proteínas y libres de gluten. Ideal para ensaladas o guarniciones saludables.",
            category = "Productos Orgánicos",
            stock = 60,
            imageRes = R.drawable.quinoa
        ),
        Product(
            id = "VR003",
            name = "Pimiento Tricolor",
            price = 2500.0,
            description = "Pack de pimientos rojos, verdes y amarillos, frescos y dulces. Perfectos para cocinar o preparar ensaladas coloridas.",
            category = "Verduras Orgánicas",
            stock = 90,
            imageRes = R.drawable.pimiento_tricolor
        ),
        Product(
            id = "PL002",
            name = "Mantequilla Natural",
            price = 2800.0,
            description = "Mantequilla 100% natural, sin aditivos, elaborada con leche fresca de campo.",
            category = "Productos Lácteos",
            stock = 40,
            imageRes = R.drawable.mantequilla
        )
    )
}
