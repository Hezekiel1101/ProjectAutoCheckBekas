package id.ac.stmi.autocekbekas

object DataMobil {
    // Fokus: Mobil Penumpang & Pickup Ringan (Tanpa Truk Berat / Bus)
    val dataset = mapOf(
        "Toyota" to mapOf(
            "Avanza" to listOf("E", "G", "V", "Veloz", "Type S", "GR Sport"),
            "Kijang" to listOf("LGX", "SGX", "SSX", "Krista"),
            "Innova" to listOf("E", "G", "V", "Q", "Zenix", "Venturer"),
            "Rush" to listOf("G", "S", "TRD Sportivo", "GR Sport"),
            "Agya" to listOf("E", "G", "TRD", "GR Sport"),
            "Calya" to listOf("E", "G"),
            "Yaris" to listOf("E", "G", "S", "TRD", "Cross", "Hebat"),
            "Vios" to listOf("E", "G", "TRD", "Limited"),
            "Fortuner" to listOf("G", "VRZ", "SRZ", "TRD", "GR Sport"),
            "Hilux" to listOf("Single Cab", "Double Cab", "G", "V", "TRD"),
            "Alphard" to listOf("G", "V", "SC", "Executive Lounge"),
            "Vellfire" to listOf("G", "V", "Z"),
            "Raize" to listOf("G", "GR Sport"),
            "Corolla Cross" to listOf("G", "V", "HEV"),
            "Sienta" to listOf("G", "V", "Q"),
            "Voxy" to listOf("G", "V"),
            "C-HR" to listOf("G", "V", "GR Sport"),
            "Camry" to listOf("G", "V", "Hybrid"),
            "Supra" to listOf("RZ", "GR"),
            "86" to listOf("GT", "GR"),
            "Townace" to listOf("Blind Van", "Pick Up")
        ),
        "Honda" to mapOf(
            "Brio" to listOf("Satya E", "Satya RS", "Rocket", "Prestige"),
            "Mobilio" to listOf("S", "E", "RS", "Prestige"),
            "BR-V" to listOf("S", "E", "Prestige", "N7X"),
            "HR-V" to listOf("S", "E", "SE", "Prestige", "RS"),
            "CR-V" to listOf("2.0", "2.4", "Prestige", "Black Edition"),
            "Jazz" to listOf("S", "E", "RS", "Ultimate"),
            "City" to listOf("S", "E", "RS", "Hatchback"),
            "Civic" to listOf("1.8", "2.0", "Type R", "RS"),
            "Accord" to listOf("2.0", "2.4", "Hybrid"),
            "WR-V" to listOf("E", "RS")
        ),
        "Daihatsu" to mapOf(
            "Xenia" to listOf("D", "M", "X", "R"),
            "Ayla" to listOf("D", "M", "X", "R"),
            "Terios" to listOf("TX", "TS", "R", "Adventure"),
            "Gran Max" to listOf("Blind Van", "Pick Up", "Microbus"),
            "Rocky" to listOf("X", "R", "ADS"),
            "Sirion" to listOf("D", "M", "X"),
            "Luxio" to listOf("D", "M", "X")
        ),
        "Suzuki" to mapOf(
            "Ertiga" to listOf("GA", "GL", "GX", "Sport", "Hybrid"),
            "Swift" to listOf("GL", "GX", "GT", "Sport"),
            "Ignis" to listOf("GL", "GX", "Sport"),
            "Carry" to listOf("Pick Up", "Blind Van", "Wide"),
            "Baleno" to listOf("GL", "GX", "RS", "Hatchback"),
            "XL7" to listOf("Zeta", "Beta", "Alpha"),
            "Jimny" to listOf("GL", "GX", "Sierra"),
            "APV" to listOf("Blind Van", "Minibus", "Luxury")
        ),
        "Mitsubishi" to mapOf(
            "Xpander" to listOf("Exceed", "GLS", "Ultimate", "Sport", "Cross"),
            "Pajero Sport" to listOf("GLX", "Exceed", "Dakar", "Ultimate"),
            "L300" to listOf("Pick Up", "Blind Van", "Minibus"),
            "Triton" to listOf("Single Cab", "Double Cab", "Exceed", "Ultimate"),
            "Outlander" to listOf("GLX", "Pajero Sport", "PHEV"),
            "Eclipse Cross" to listOf("GLS", "Ultimate")
        ),
        "Nissan" to mapOf(
            "Livina" to listOf("XE", "XL", "XV", "Ultimate"),
            "X-Trail" to listOf("ST", "XT", "T32", "e-Power"),
            "Juke" to listOf("RX", "CVT", "Nismo"),
            "Kicks" to listOf("E-Power", "Premium"),
            "March" to listOf("GL", "GX"),
            "Serena" to listOf("Highway Star", "E-Power"),
            "Navara" to listOf("Single Cab", "Double Cab", "PRO-4X")
        ),
        "Hyundai" to mapOf(
            "Creta" to listOf("Active", "Trend", "Style", "Prime"),
            "Stargazer" to listOf("Active", "Trend", "Style", "Prime"),
            "Ioniq 5" to listOf("Standard", "Long Range", "Signature"),
            "Tucson" to listOf("GL", "GLS", "Premium"),
            "Santa Fe" to listOf("GL", "GLS", "Calligraphy"),
            "Palisade" to listOf("GLS", "Calligraphy")
        ),
        "Wuling" to mapOf(
            "Confero" to listOf("S", "C"),
            "Cortez" to listOf("C", "S", "CT"),
            "Almaz" to listOf("Exclusive", "Smart Enjoy"),
            "Air EV" to listOf("Lite", "Standard", "Long Range"),
            "Formo" to listOf("Blind Van", "Pick Up"),
            "Alvez" to listOf("Exclusive", "Smart Enjoy")
        ),
        "Mazda" to mapOf(
            "2" to listOf("R", "GT"),
            "3" to listOf("Sedan", "Hatchback", "Astina"),
            "6" to listOf("Sedan", "Wagon"),
            "CX-3" to listOf("GT", "Sport"),
            "CX-5" to listOf("GT", "Touring", "Signature"),
            "CX-8" to listOf("GT", "Elite"),
            "CX-9" to listOf("GT", "Signature"),
            "MX-5" to listOf("RF", "Soft Top")
        ),
        "Ford" to mapOf(
            "Fiesta" to listOf("Trend", "Sport", "ST"),
            "Focus" to listOf("Trend", "Sport", "RS"),
            "EcoSport" to listOf("Trend", "Titanium"),
            "Everest" to listOf("Trend", "Titanium", "Wildtrak"),
            "Ranger" to listOf("Single Cab", "Double Cab", "Wildtrak", "Raptor"),
            "Mustang" to listOf("GT", "Ecoboost", "Shelby")
        ),
        "Chevrolet" to mapOf(
            "Spark" to listOf("LS", "LT"),
            "Aveo" to listOf("LS", "LT"),
            "Cruze" to listOf("LS", "LT"),
            "Captiva" to listOf("LS", "LT", "Diesel"),
            "Trailblazer" to listOf("LT", "LTZ"),
            "Spin" to listOf("LS", "LT", "LTZ")
        ),
        "BMW" to mapOf(
            "Seri 1" to listOf("118i", "120i", "M Sport"),
            "Seri 2" to listOf("218i", "220i"),
            "Seri 3" to listOf("320i", "330i", "M Sport"),
            "Seri 4" to listOf("420i", "430i"),
            "Seri 5" to listOf("520i", "530i", "M Sport"),
            "Seri 7" to listOf("730i", "740i", "750i"),
            "X1" to listOf("sDrive18i", "sDrive20i"),
            "X2" to listOf("sDrive18i", "sDrive20i"),
            "X3" to listOf("xDrive20i", "xDrive30i"),
            "X4" to listOf("xDrive20i", "xDrive30i"),
            "X5" to listOf("xDrive40i", "M Sport"),
            "X6" to listOf("xDrive40i", "M Sport"),
            "X7" to listOf("xDrive40i", "M Sport"),
            "i3" to listOf("Standard", "S"),
            "iX" to listOf("xDrive40", "xDrive50"),
            "M3" to listOf("Standard", "Competition"),
            "M4" to listOf("Standard", "Competition"),
            "M5" to listOf("Standard", "Competition")
        ),
        "Mercedes-Benz" to mapOf(
            "A-Class" to listOf("A200", "A250", "AMG"),
            "C-Class" to listOf("C200", "C250", "C300", "AMG"),
            "E-Class" to listOf("E200", "E250", "E300", "AMG"),
            "S-Class" to listOf("S400", "S450", "S500", "Maybach"),
            "CLA" to listOf("CLA200", "CLA250", "AMG"),
            "CLS" to listOf("CLS300", "CLS400", "AMG"),
            "GLA" to listOf("GLA200", "GLA250", "AMG"),
            "GLB" to listOf("GLB200", "GLB250", "AMG"),
            "GLC" to listOf("GLC200", "GLC300", "AMG"),
            "GLE" to listOf("GLE400", "GLE450", "AMG"),
            "GLS" to listOf("GLS400", "GLS450", "Maybach"),
            "G-Class" to listOf("G500", "G63 AMG"),
            "V-Class" to listOf("V250", "V260"),
            "EQS" to listOf("EQS450", "EQS580")
        ),
        "Audi" to mapOf(
            "A1" to listOf("Sportback", "S1"),
            "A3" to listOf("Sedan", "Sportback", "S3"),
            "A4" to listOf("Sedan", "Avant", "S4"),
            "A5" to listOf("Coupe", "Sportback", "S5"),
            "A6" to listOf("Sedan", "Avant", "S6"),
            "A7" to listOf("Sportback", "S7"),
            "A8" to listOf("L", "S8"),
            "Q2" to listOf("Standard", "SQ2"),
            "Q3" to listOf("Standard", "Sportback", "RS Q3"),
            "Q5" to listOf("Standard", "SQ5"),
            "Q7" to listOf("Standard", "SQ7"),
            "Q8" to listOf("Standard", "RS Q8"),
            "e-tron" to listOf("50 Quattro", "55 Quattro", "Sportback")
        ),
        "Isuzu" to mapOf(
            "Panther" to listOf("LS", "LM", "LV"),
            "Traga" to listOf("Single Cab", "Double Cab"),
            "MU-X" to listOf("Standard", "Ultimate"),
            "D-Max" to listOf("Single Cab", "Double Cab", "V-Cross")
        ),
        "Kia" to mapOf(
            "Rio" to listOf("LS", "EX", "SX"),
            "Picanto" to listOf("EX", "GT Line"),
            "Seltos" to listOf("EX", "Intellectual", "GT Line"),
            "Sonet" to listOf("EX", "Intellectual", "GT Line"),
            "Sportage" to listOf("EX", "SX", "GT Line"),
            "Sorento" to listOf("EX", "SX", "GT Line"),
            "Carnival" to listOf("EX", "SX", "Prestige"),
            "EV6" to listOf("GT Line", "GT")
        ),
        "Peugeot" to mapOf(
            "208" to listOf("Active", "Allure", "GT Line"),
            "308" to listOf("Active", "Allure", "GT"),
            "408" to listOf("Allure", "GT"),
            "2008" to listOf("Active", "Allure", "GT"),
            "3008" to listOf("Active", "Allure", "GT"),
            "5008" to listOf("Allure", "GT")
        ),
        "Renault" to mapOf(
            "Clio" to listOf("Authentique", "Expression", "RS"),
            "Megane" to listOf("Expression", "GT", "RS"),
            "Captur" to listOf("Expression", "Intens"),
            "Duster" to listOf("Expression", "Intens"),
            "Koleos" to listOf("Expression", "Intens")
        ),
        "Volkswagen" to mapOf(
            "Polo" to listOf("Trendline", "Comfortline", "GTI"),
            "Golf" to listOf("Trendline", "Comfortline", "GTI", "R"),
            "Tiguan" to listOf("Trendline", "Comfortline", "R-Line"),
            "Touareg" to listOf("Comfortline", "R-Line"),
            "Passat" to listOf("Comfortline", "Highline", "R-Line"),
            "Arteon" to listOf("Elegance", "R-Line")
        ),
        "Mini" to mapOf(
            "Cooper" to listOf("3 Door", "5 Door", "S", "JCW"),
            "Countryman" to listOf("Cooper", "Cooper S", "JCW"),
            "Clubman" to listOf("Cooper", "Cooper S", "JCW"),
            "Convertible" to listOf("Cooper", "Cooper S")
        ),
        "Jeep" to mapOf(
            "Wrangler" to listOf("Sport", "Sahara", "Rubicon"),
            "Compass" to listOf("Sport", "Limited", "Trailhawk"),
            "Grand Cherokee" to listOf("Laredo", "Limited", "Summit", "Trackhawk"),
            "Cherokee" to listOf("Sport", "Limited", "Trailhawk"),
            "Renegade" to listOf("Sport", "Limited", "Trailhawk")
        ),
        "Subaru" to mapOf(
            "Impreza" to listOf("1.6", "2.0", "WRX", "STI"),
            "Forester" to listOf("2.0", "2.0 XT", "STI"),
            "XV" to listOf("2.0", "GT"),
            "Outback" to listOf("2.5", "3.6"),
            "BRZ" to listOf("Standard", "TS")
        ),
        "Lexus" to mapOf(
            "UX" to listOf("UX200", "UX250h"),
            "NX" to listOf("NX200", "NX300", "NX350"),
            "RX" to listOf("RX300", "RX350", "RX450h"),
            "GX" to listOf("GX400", "GX460"),
            "LX" to listOf("LX570", "LX600"),
            "IS" to listOf("IS250", "IS300", "IS350"),
            "ES" to listOf("ES250", "ES300h", "ES350"),
            "LS" to listOf("LS400", "LS460", "LS500"),
            "LC" to listOf("LC500", "LC500h"),
            "LM" to listOf("LM300h", "LM350")
        ),
        "MG" to mapOf(
            "MG5" to listOf("Exclusive", "Magnify"),
            "MG6" to listOf("Fastback", "Trophy"),
            "ZS" to listOf("Exclusive", "Magnify", "Ignite"),
            "HS" to listOf("Exclusive", "Magnify", "Ignite"),
            "RX5" to listOf("Exclusive", "Magnify"),
            "MG4 EV" to listOf("Exclusive", "Magnify"),
            "MG ZS EV" to listOf("Exclusive", "Magnify")
        ),
        "Chery" to mapOf(
            "Tiggo 4 Pro" to listOf("Comfort", "Luxury", "Premium"),
            "Tiggo 7 Pro" to listOf("Comfort", "Luxury", "Premium"),
            "Tiggo 8 Pro" to listOf("Comfort", "Luxury", "Premium"),
            "Omoda 5" to listOf("GT", "Premium"),
            "QQ" to listOf("Standard", "Comfort")
        ),
        "BYD" to mapOf(
            "Atto 3" to listOf("Superior", "Premium"),
            "Dolphin" to listOf("Dynamic", "Premium"),
            "Seal" to listOf("Performance", "Premium"),
            "M6" to listOf("Standard", "Superior")
        ),
        "Neta" to mapOf(
            "V" to listOf("Standard", "Premium"),
            "U" to listOf("Standard", "Premium"),
            "S" to listOf("Standard", "Premium")
        ),
        "DFSK" to mapOf(
            "Glory 580" to listOf("Comfort", "Luxury"),
            "Gelora" to listOf("Standard", "Luxury"),
            "Super Cab" to listOf("Standard", "Luxury"),
            "E1" to listOf("Standard", "Premium")
        ),
        "Citroen" to mapOf(
            "C3" to listOf("Live", "Feel", "Shine"),
            "C4" to listOf("Live", "Feel", "Shine"),
            "C5 Aircross" to listOf("Feel", "Shine")
        ),
        "Volvo" to mapOf(
            "XC40" to listOf("Momentum", "R-Design", "Inscription"),
            "XC60" to listOf("Momentum", "R-Design", "Inscription"),
            "XC90" to listOf("Momentum", "R-Design", "Inscription")
        ),
        "Land Rover" to mapOf(
            "Range Rover" to listOf("Vogue", "HSE", "Autobiography"),
            "Range Rover Evoque" to listOf("SE", "HSE", "R-Dynamic"),
            "Range Rover Sport" to listOf("SE", "HSE", "Autobiography"),
            "Discovery" to listOf("S", "SE", "HSE"),
            "Defender" to listOf("Standard", "110", "90")
        ),
        "Jaguar" to mapOf(
            "XE" to listOf("Pure", "Prestige", "R-Sport"),
            "XF" to listOf("Pure", "Prestige", "R-Sport"),
            "XJ" to listOf("Luxury", "Premium", "Portfolio"),
            "F-Pace" to listOf("Prestige", "R-Sport", "SVR"),
            "I-Pace" to listOf("S", "SE", "HSE")
        ),
        "Porsche" to mapOf(
            "911" to listOf("Carrera", "Carrera S", "Turbo"),
            "Macan" to listOf("Standard", "S", "GTS"),
            "Cayenne" to listOf("Standard", "S", "Turbo"),
            "Taycan" to listOf("4S", "Turbo", "Turbo S"),
            "Panamera" to listOf("Standard", "4S", "Turbo")
        )
    )

    fun getMerekList(): List<String> = dataset.keys.toList()

    fun getModelList(merek: String): List<String> {
        return dataset[merek]?.keys?.toList() ?: emptyList()
    }

    fun getTipeList(merek: String, model: String): List<String> {
        return dataset[merek]?.get(model) ?: emptyList()
    }
}