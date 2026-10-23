package com.royals.class11physics

data class FormulaItem(
    val id: String,
    val chapterNumber: Int,
    val chapterTitle: String,
    val topic: String,
    val formula: String,
    val description: String,
    val symbolMeanings: String,
    val siUnit: String = "",
    val dimensionalFormula: String = "",
    val examImportance: String = "Crucial" // "Crucial", "Must Know", "High Yield"
)

data class PhysicalConstant(
    val name: String,
    val symbol: String,
    val value: String,
    val unit: String,
    val category: String
)

data class QuantityDimension(
    val quantity: String,
    val symbol: String,
    val formula: String,
    val siUnit: String,
    val dimensionalFormula: String
)

val physicalConstants = listOf(
    PhysicalConstant("Acceleration due to Gravity (Earth)", "g", "9.8 m/s²", "m/s²", "Mechanics (Ch 3, 4, 8)"),
    PhysicalConstant("Universal Gravitational Constant", "G", "6.67 × 10⁻¹¹", "N·m²/kg²", "Gravitation (Ch 8)"),
    PhysicalConstant("Universal Gas Constant", "R", "8.314", "J/(mol·K)", "Thermal & Gas (Ch 12, 13)"),
    PhysicalConstant("Boltzmann Constant", "k_B", "1.38 × 10⁻²³", "J/K", "Kinetic Theory (Ch 13)"),
    PhysicalConstant("Avogadro's Number", "N_A", "6.022 × 10²³", "mol⁻¹", "Kinetic Theory (Ch 13)"),
    PhysicalConstant("Standard Atmospheric Pressure", "1 atm", "1.013 × 10⁵ (≈ 10⁵)", "Pa (N/m²)", "Fluids (Ch 10)"),
    PhysicalConstant("Density of Pure Water (at 4°C)", "ρ_w", "1000", "kg/m³", "Fluids (Ch 10)"),
    PhysicalConstant("Stefan-Boltzmann Constant", "σ", "5.67 × 10⁻⁸", "W/(m²·K⁴)", "Thermal Properties (Ch 11)"),
    PhysicalConstant("Wien's Displacement Constant", "b", "2.898 × 10⁻³", "m·K", "Thermal Properties (Ch 11)"),
    PhysicalConstant("Speed of Sound in Air at STP", "v", "332", "m/s", "Waves (Ch 15)"),
    PhysicalConstant("Latent Heat of Fusion of Ice", "L_f", "3.33 × 10⁵", "J/kg", "Thermal Properties (Ch 11)"),
    PhysicalConstant("Latent Heat of Vaporization (Water)", "L_v", "2.26 × 10⁶", "J/kg", "Thermal Properties (Ch 11)")
)

val quantityDimensions = listOf(
    QuantityDimension("Force", "F", "F = m · a", "newton (N)", "[M L T⁻²]"),
    QuantityDimension("Work / Energy / Heat", "W, E, Q", "W = F · d", "joule (J)", "[M L² T⁻²]"),
    QuantityDimension("Power", "P", "P = W / t", "watt (W)", "[M L² T⁻³]"),
    QuantityDimension("Linear Momentum", "p", "p = m · v", "kg·m/s", "[M L T⁻¹]"),
    QuantityDimension("Impulse", "J", "J = F · Δt", "N·s", "[M L T⁻¹]"),
    QuantityDimension("Pressure / Stress", "P, σ", "P = F / A", "pascal (Pa)", "[M L⁻¹ T⁻²]"),
    QuantityDimension("Torque (Moment of Force)", "τ", "τ = r × F", "N·m", "[M L² T⁻²]"),
    QuantityDimension("Moment of Inertia", "I", "I = m · r²", "kg·m²", "[M L² T⁰]"),
    QuantityDimension("Angular Momentum", "L", "L = r × p", "J·s (kg·m²/s)", "[M L² T⁻¹]"),
    QuantityDimension("Angular Velocity", "ω", "ω = θ / t", "rad/s", "[M⁰ L⁰ T⁻¹]"),
    QuantityDimension("Angular Acceleration", "α", "α = dω / dt", "rad/s²", "[M⁰ L⁰ T⁻²]"),
    QuantityDimension("Surface Tension", "T, S", "T = F / L", "N/m (J/m²)", "[M L⁰ T⁻²]"),
    QuantityDimension("Coefficient of Viscosity", "η", "η = F / (A · dv/dx)", "Pa·s (poiseuille)", "[M L⁻¹ T⁻¹]"),
    QuantityDimension("Modulus of Elasticity (Young's)", "Y, B, η", "Y = Stress / Strain", "N/m² (Pa)", "[M L⁻¹ T⁻²]"),
    QuantityDimension("Strain", "ε", "ε = ΔL / L", "Dimensionless", "[M⁰ L⁰ T⁰]"),
    QuantityDimension("Universal Gravitational Constant", "G", "G = F·r² / (m₁·m₂)", "N·m²/kg²", "[M⁻¹ L³ T⁻²]"),
    QuantityDimension("Specific Heat Capacity", "c, s", "c = Q / (m · ΔT)", "J/(kg·K)", "[M⁰ L² T⁻² K⁻¹]"),
    QuantityDimension("Latent Heat", "L", "L = Q / m", "J/kg", "[M⁰ L² T⁻²]"),
    QuantityDimension("Thermal Conductivity", "k, K", "k = (Q·L) / (A·ΔT·t)", "W/(m·K)", "[M L T⁻³ K⁻¹]"),
    QuantityDimension("Universal Gas Constant", "R", "R = (P·V) / (n·T)", "J/(mol·K)", "[M L² T⁻² K⁻¹ mol⁻¹]"),
    QuantityDimension("Boltzmann Constant", "k_B", "k_B = R / N_A", "J/K", "[M L² T⁻² K⁻¹]"),
    QuantityDimension("Stefan-Boltzmann Constant", "σ", "σ = E / (A · t · T⁴)", "W/(m²·K⁴)", "[M L⁰ T⁻³ K⁻⁴]"),
    QuantityDimension("Spring Constant (Stiffness)", "k", "k = F / x", "N/m", "[M L⁰ T⁻²]"),
    QuantityDimension("Frequency / Wave Frequency", "f, ν", "f = 1 / T", "hertz (Hz)", "[M⁰ L⁰ T⁻¹]"),
    QuantityDimension("Wave Number", "k, ν̄", "k = 2π / λ", "m⁻¹", "[M⁰ L⁻¹ T⁰]")
)

val physicsFormulas = listOf(
    // -------------------------------------------------------------
    // CHAPTER 1 & 2: UNITS AND MEASUREMENTS
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch1_1",
        chapterNumber = 1,
        chapterTitle = "Physical World & Measurement",
        topic = "Least Count of Vernier Caliper",
        formula = "LC = 1 MSD - 1 VSD = (1 - m/n) · MSD = s / n",
        description = "Smallest value that can be accurately measured by a vernier caliper.",
        symbolMeanings = "MSD = Main Scale Division, VSD = Vernier Scale Division, n = total vernier divisions matching m main scale divisions, s = value of 1 main scale division.",
        siUnit = "meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Must Know"
    ),
    FormulaItem(
        id = "f_ch1_2",
        chapterNumber = 1,
        chapterTitle = "Physical World & Measurement",
        topic = "Least Count of Screw Gauge (Micrometer)",
        formula = "LC = Pitch / (Number of Circular Scale Divisions)",
        description = "Smallest displacement measurable along the main axis per circular division.",
        symbolMeanings = "Pitch = Linear distance moved by screw per complete rotation.",
        siUnit = "meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Must Know"
    ),
    FormulaItem(
        id = "f_ch2_1",
        chapterNumber = 2,
        chapterTitle = "Units and Measurements",
        topic = "Combination of Errors (Multiplication & Division)",
        formula = "ΔZ / Z = (ΔA / A) + (ΔB / B)",
        description = "Relative fractional errors always add up when multiplying or dividing physical quantities (Z = A · B or Z = A / B).",
        symbolMeanings = "Z = calculated quantity, ΔZ = absolute error in Z, A and B = measured quantities.",
        siUnit = "Dimensionless",
        dimensionalFormula = "[M⁰ L⁰ T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch2_2",
        chapterNumber = 2,
        chapterTitle = "Units and Measurements",
        topic = "Error in Quantity Raised to a Power",
        formula = "For Z = Aᵖ · Bʳ / Cˢ : ΔZ / Z = p(ΔA/A) + r(ΔB/B) + s(ΔC/C)",
        description = "Relative error in a quantity raised to power p is p times the relative error in that quantity.",
        symbolMeanings = "p, r, s = power exponents of measured quantities A, B, and C.",
        siUnit = "Dimensionless",
        dimensionalFormula = "[M⁰ L⁰ T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch2_3",
        chapterNumber = 2,
        chapterTitle = "Units and Measurements",
        topic = "Conversion of Units (Dimensional Method)",
        formula = "n₂ = n₁ · [M₁/M₂]ᵃ · [L₁/L₂]ᵇ · [T₁/T₂]ᶜ",
        description = "Conversion formula from one unit system to another using dimensions [Mᵃ Lᵇ Tᶜ].",
        symbolMeanings = "n₁, n₂ = numerical values in system 1 and system 2; M₁, L₁, T₁ and M₂, L₂, T₂ = fundamental units.",
        siUnit = "Depends on quantity",
        dimensionalFormula = "[Mᵃ Lᵇ Tᶜ]",
        examImportance = "High Yield"
    ),

    // -------------------------------------------------------------
    // CHAPTER 3: MOTION IN A STRAIGHT LINE
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch3_1",
        chapterNumber = 3,
        chapterTitle = "Motion in a Straight Line",
        topic = "Kinematic Equations for Constant Acceleration",
        formula = "v = u + at \ns = ut + ½ at² \nv² = u² + 2as",
        description = "Three primary equations of motion governing linear motion under uniform acceleration.",
        symbolMeanings = "u = initial velocity, v = final velocity, a = constant acceleration, t = time interval, s = displacement.",
        siUnit = "v: m/s, s: m, a: m/s²",
        dimensionalFormula = "v: [L T⁻¹], s: [L], a: [L T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch3_2",
        chapterNumber = 3,
        chapterTitle = "Motion in a Straight Line",
        topic = "Displacement in the n-th Second",
        formula = "sₙ = u + a/2 · (2n - 1)",
        description = "Calculates the exact distance traveled during the specific n-th second of motion.",
        symbolMeanings = "sₙ = distance traveled in n-th second, u = initial velocity, a = acceleration, n = time in seconds (integer).",
        siUnit = "meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch3_3",
        chapterNumber = 3,
        chapterTitle = "Motion in a Straight Line",
        topic = "Stopping Distance and Stopping Time",
        formula = "d_s = u² / (2a) \nt_s = u / a",
        description = "Distance and time required for a vehicle moving at speed u to stop under constant deceleration a.",
        symbolMeanings = "d_s = stopping distance, t_s = stopping time, u = initial speed, a = retardation magnitude.",
        siUnit = "d_s: m, t_s: s",
        dimensionalFormula = "d_s: [L], t_s: [T]",
        examImportance = "High Yield"
    ),
    FormulaItem(
        id = "f_ch3_4",
        chapterNumber = 3,
        chapterTitle = "Motion in a Straight Line",
        topic = "Relative Velocity in 1D",
        formula = "v_AB = v_A - v_B",
        description = "Velocity of body A with respect to body B in one-dimensional motion.",
        symbolMeanings = "v_AB = relative velocity of A relative to B, v_A = velocity of A, v_B = velocity of B.",
        siUnit = "m/s",
        dimensionalFormula = "[M⁰ L¹ T⁻¹]",
        examImportance = "Must Know"
    ),

    // -------------------------------------------------------------
    // CHAPTER 4: MOTION IN A PLANE
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch4_1",
        chapterNumber = 4,
        chapterTitle = "Motion in a Plane",
        topic = "Resultant of Two Vectors (Parallelogram Law)",
        formula = "R = √(A² + B² + 2AB cos θ) \ntan α = (B sin θ) / (A + B cos θ)",
        description = "Magnitude R and direction α of the resultant of two vectors A and B inclined at angle θ.",
        symbolMeanings = "R = resultant magnitude, A, B = vector magnitudes, θ = angle between A and B, α = angle of R with vector A.",
        siUnit = "Same as vectors A and B",
        dimensionalFormula = "Same as vectors",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch4_2",
        chapterNumber = 4,
        chapterTitle = "Motion in a Plane",
        topic = "Projectile Motion: Time of Flight",
        formula = "T = (2u sin θ) / g",
        description = "Total time the projectile remains airborne before landing on the horizontal plane.",
        symbolMeanings = "T = time of flight, u = projection speed, θ = angle of projection with horizontal, g = acceleration due to gravity.",
        siUnit = "second (s)",
        dimensionalFormula = "[M⁰ L⁰ T¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch4_3",
        chapterNumber = 4,
        chapterTitle = "Motion in a Plane",
        topic = "Projectile Motion: Maximum Height",
        formula = "H = (u² sin² θ) / (2g)",
        description = "Peak vertical displacement achieved by a projectile.",
        symbolMeanings = "H = maximum height, u = launch speed, θ = launch angle, g = 9.8 m/s².",
        siUnit = "meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch4_4",
        chapterNumber = 4,
        chapterTitle = "Motion in a Plane",
        topic = "Projectile Motion: Horizontal Range",
        formula = "R = (u² sin 2θ) / g \nR_max = u² / g  (at θ = 45°)",
        description = "Total horizontal distance traversed by the projectile. Range is equal for complementary angles θ and (90° - θ).",
        symbolMeanings = "R = horizontal range, R_max = maximum horizontal range at 45° launch angle.",
        siUnit = "meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch4_5",
        chapterNumber = 4,
        chapterTitle = "Motion in a Plane",
        topic = "Trajectory Equation of Projectile",
        formula = "y = x tan θ - (g x²) / (2 u² cos² θ) = x tan θ · [1 - x/R]",
        description = "Parabolic equation describing vertical position y as a function of horizontal position x.",
        symbolMeanings = "x, y = coordinates, θ = projection angle, u = launch speed, R = horizontal range.",
        siUnit = "meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch4_6",
        chapterNumber = 4,
        chapterTitle = "Motion in a Plane",
        topic = "Centripetal Acceleration in Uniform Circular Motion",
        formula = "a_c = v² / r = ω² r = 4π² f² r = (4π² / T²) · r",
        description = "Radial acceleration directed toward the center of circular path of radius r.",
        symbolMeanings = "a_c = centripetal acceleration, v = tangential speed, ω = angular velocity, r = radius, f = frequency, T = time period.",
        siUnit = "m/s²",
        dimensionalFormula = "[M⁰ L¹ T⁻²]",
        examImportance = "Crucial"
    ),

    // -------------------------------------------------------------
    // CHAPTER 5: LAWS OF MOTION
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch5_1",
        chapterNumber = 5,
        chapterTitle = "Laws of Motion",
        topic = "Newton's Second Law & Momentum",
        formula = "F_net = dp/dt = m · a \nJ = ∫ F dt = Δp = m(v - u)",
        description = "Net force equals rate of change of linear momentum; Impulse J equals total change in momentum.",
        symbolMeanings = "F_net = resultant external force, p = linear momentum (m·v), a = acceleration, J = impulse.",
        siUnit = "F: N, J: N·s",
        dimensionalFormula = "F: [M L T⁻²], J: [M L T⁻¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch5_2",
        chapterNumber = 5,
        chapterTitle = "Laws of Motion",
        topic = "Friction Laws (Static & Kinetic)",
        formula = "f_s ≤ μ_s N  (f_s,max = μ_s N) \nf_k = μ_k N \nμ_s = tan λ = tan θ_r",
        description = "Limiting static friction is proportional to normal reaction N. Angle of friction λ equals angle of repose θ_r.",
        symbolMeanings = "f_s = static friction, f_k = kinetic friction, μ_s = coeff. of static friction, μ_k = coeff. of kinetic friction, N = normal force.",
        siUnit = "Force: N, μ: Dimensionless",
        dimensionalFormula = "f: [M L T⁻²], μ: [M⁰ L⁰ T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch5_3",
        chapterNumber = 5,
        chapterTitle = "Laws of Motion",
        topic = "Banking of Curved Roads (Max Safe Speed)",
        formula = "v_optimum = √(r g tan θ) \nv_max = √[ r g · (μ_s + tan θ) / (1 - μ_s tan θ) ]",
        description = "Optimum speed prevents tire wear without friction; v_max includes friction on curved road of radius r banked at angle θ.",
        symbolMeanings = "r = radius of curvature, g = 9.8 m/s², θ = angle of banking, μ_s = coefficient of friction.",
        siUnit = "m/s",
        dimensionalFormula = "[M⁰ L¹ T⁻¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch5_4",
        chapterNumber = 5,
        chapterTitle = "Laws of Motion",
        topic = "Apparent Weight in an Accelerating Elevator",
        formula = "Upward acc (a): R = m(g + a) \nDownward acc (a): R = m(g - a) \nFree fall (a = g): R = 0 (Weightlessness)",
        description = "Effective normal reaction on a person of mass m standing inside an accelerating lift.",
        symbolMeanings = "R = normal reaction / scale reading, m = mass of body, g = acceleration due to gravity, a = lift acceleration.",
        siUnit = "newton (N)",
        dimensionalFormula = "[M L T⁻²]",
        examImportance = "Must Know"
    ),

    // -------------------------------------------------------------
    // CHAPTER 6: WORK, ENERGY AND POWER
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch6_1",
        chapterNumber = 6,
        chapterTitle = "Work, Energy and Power",
        topic = "Work Done by Constant & Variable Force",
        formula = "W = F · d = F d cos θ \nW = ∫_{x_i}^{x_f} F(x) dx",
        description = "Scalar product of force and displacement vector, or area under the Force-Displacement graph.",
        symbolMeanings = "W = work done, F = force magnitude, d = displacement, θ = angle between force and displacement.",
        siUnit = "joule (J) = N·m",
        dimensionalFormula = "[M L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch6_2",
        chapterNumber = 6,
        chapterTitle = "Work, Energy and Power",
        topic = "Work-Energy Theorem",
        formula = "W_net = ΔK = ½ m v² - ½ m u²",
        description = "Work done by all forces (conservative, non-conservative, external) equals change in kinetic energy.",
        symbolMeanings = "W_net = total work done, ΔK = change in kinetic energy, m = mass, u = initial velocity, v = final velocity.",
        siUnit = "joule (J)",
        dimensionalFormula = "[M L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch6_3",
        chapterNumber = 6,
        chapterTitle = "Work, Energy and Power",
        topic = "Potential Energy of an Ideal Spring",
        formula = "U_s = ½ k x² \nF_s = -k x",
        description = "Hooke's restoring force and elastic strain energy stored in a spring stretched or compressed by displacement x.",
        symbolMeanings = "k = spring force constant (stiffness), x = elongation or compression from natural length.",
        siUnit = "joule (J)",
        dimensionalFormula = "[M L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch6_4",
        chapterNumber = 6,
        chapterTitle = "Work, Energy and Power",
        topic = "Power and Mechanical Advantage",
        formula = "P_avg = ΔW / Δt \nP_inst = dW/dt = F · v = F v cos θ",
        description = "Rate at which work is done or energy is transferred.",
        symbolMeanings = "P = power, W = work, t = time, F = force, v = instantaneous velocity.",
        siUnit = "watt (W) = J/s",
        dimensionalFormula = "[M L² T⁻³]",
        examImportance = "Must Know"
    ),
    FormulaItem(
        id = "f_ch6_5",
        chapterNumber = 6,
        chapterTitle = "Work, Energy and Power",
        topic = "1D Perfectly Elastic Collision",
        formula = "v₁ = [(m₁ - m₂)/(m₁ + m₂)] u₁ + [2m₂/(m₁ + m₂)] u₂ \nv₂ = [2m₁/(m₁ + m₂)] u₁ + [(m₂ - m₁)/(m₁ + m₂)] u₂",
        description = "Final velocities of two masses m₁ and m₂ after head-on elastic collision conserving both momentum and kinetic energy.",
        symbolMeanings = "u₁, u₂ = initial velocities, v₁, v₂ = final velocities of masses m₁ and m₂.",
        siUnit = "m/s",
        dimensionalFormula = "[M⁰ L¹ T⁻¹]",
        examImportance = "Crucial"
    ),

    // -------------------------------------------------------------
    // CHAPTER 7: SYSTEM OF PARTICLES AND ROTATIONAL MOTION
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch7_1",
        chapterNumber = 7,
        chapterTitle = "System of Particles and Rotational Motion",
        topic = "Center of Mass (System of Particles)",
        formula = "R_cm = (∑ m_i r_i) / (∑ m_i) = (1/M) ∫ r dm",
        description = "Position vector of the mass center where the whole system mass M behaves as concentrated.",
        symbolMeanings = "m_i = individual particle mass, r_i = particle position vector, M = total mass.",
        siUnit = "meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch7_2",
        chapterNumber = 7,
        chapterTitle = "System of Particles and Rotational Motion",
        topic = "Torque & Angular Momentum",
        formula = "τ = r × F = I α = dL/dt \nL = r × p = I ω",
        description = "Rotational analogue of Newton's second law and linear momentum. In absence of external torque (τ_ext = 0), L is conserved.",
        symbolMeanings = "τ = torque, r = position vector from axis, F = applied force, I = moment of inertia, α = angular acceleration, L = angular momentum, ω = angular velocity.",
        siUnit = "τ: N·m, L: J·s (kg·m²/s)",
        dimensionalFormula = "τ: [M L² T⁻²], L: [M L² T⁻¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch7_3",
        chapterNumber = 7,
        chapterTitle = "System of Particles and Rotational Motion",
        topic = "Theorems of Moment of Inertia",
        formula = "Parallel Axis: I = I_cm + M d² \nPerpendicular Axis (Lamina): I_z = I_x + I_y",
        description = "Calculate moment of inertia about any parallel axis at distance d from center of mass, or perpendicular axis in planar lamina.",
        symbolMeanings = "I = moment of inertia, I_cm = moment of inertia about centroidal axis, M = total mass, d = distance between parallel axes.",
        siUnit = "kg·m²",
        dimensionalFormula = "[M L² T⁰]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch7_4",
        chapterNumber = 7,
        chapterTitle = "System of Particles and Rotational Motion",
        topic = "Pure Rolling Motion Kinetic Energy & Acceleration",
        formula = "K_total = K_trans + K_rot = ½ M v_cm² · (1 + k²/R²) \na = (g sin θ) / (1 + k²/R²)",
        description = "Total kinetic energy and acceleration down an incline of angle θ for a rolling symmetrical body without slipping.",
        symbolMeanings = "v_cm = speed of center of mass, k = radius of gyration, R = body radius, (k²/R² = ½ for cylinder/disc, ⅖ for solid sphere, 1 for ring/hoop).",
        siUnit = "K: J, a: m/s²",
        dimensionalFormula = "K: [M L² T⁻²], a: [L T⁻²]",
        examImportance = "Crucial"
    ),

    // -------------------------------------------------------------
    // CHAPTER 8: GRAVITATION
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch8_1",
        chapterNumber = 8,
        chapterTitle = "Gravitation",
        topic = "Acceleration Due to Gravity & Altitude Variation",
        formula = "g = G M / R² \ng_h = g · [1 + h/R]⁻² ≈ g · (1 - 2h/R)  [for h ≪ R]",
        description = "Variation of Earth's gravitational acceleration at an elevation h above the surface.",
        symbolMeanings = "g = 9.8 m/s² on surface, G = 6.674×10⁻¹¹ N·m²/kg², M = mass of Earth (6×10²⁴ kg), R = Earth radius (6400 km), h = height above surface.",
        siUnit = "m/s²",
        dimensionalFormula = "[M⁰ L¹ T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch8_2",
        chapterNumber = 8,
        chapterTitle = "Gravitation",
        topic = "Variation of g with Depth",
        formula = "g_d = g · (1 - d/R)",
        description = "Gravitational acceleration at depth d below Earth's surface. At Earth center (d = R), g = 0.",
        symbolMeanings = "g_d = acceleration at depth d, d = depth below ground, R = Earth radius.",
        siUnit = "m/s²",
        dimensionalFormula = "[M⁰ L¹ T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch8_3",
        chapterNumber = 8,
        chapterTitle = "Gravitation",
        topic = "Gravitational Potential Energy & Potential",
        formula = "U(r) = - (G M m) / r \nV(r) = - (G M) / r",
        description = "Work done in bringing mass m from infinity to radial distance r in the gravitational field of mass M.",
        symbolMeanings = "U = gravitational potential energy, V = gravitational potential, r = distance from center.",
        siUnit = "U: J, V: J/kg",
        dimensionalFormula = "U: [M L² T⁻²], V: [L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch8_4",
        chapterNumber = 8,
        chapterTitle = "Gravitation",
        topic = "Escape Velocity and Orbital Velocity",
        formula = "v_e = √(2GM / R) = √(2 g R) ≈ 11.2 km/s \nv_o = √(GM / R) = √(g R) ≈ 7.92 km/s \nv_e = √2 · v_o",
        description = "Minimum velocity needed to escape Earth's gravity vs speed required to orbit Earth close to the surface.",
        symbolMeanings = "v_e = escape velocity, v_o = orbital velocity for near-earth orbit, R = 6.4 × 10⁶ m.",
        siUnit = "m/s (or km/s)",
        dimensionalFormula = "[M⁰ L¹ T⁻¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch8_5",
        chapterNumber = 8,
        chapterTitle = "Gravitation",
        topic = "Kepler's Third Law of Planetary Motion",
        formula = "T² = [4π² / (GM)] · r³ \nT² ∝ r³",
        description = "Square of orbital period T of a satellite/planet is proportional to the cube of semi-major axis r.",
        symbolMeanings = "T = orbital period, r = orbital radius, M = central body mass.",
        siUnit = "s²",
        dimensionalFormula = "[T²]",
        examImportance = "Must Know"
    ),

    // -------------------------------------------------------------
    // CHAPTER 9: MECHANICAL PROPERTIES OF SOLIDS
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch9_1",
        chapterNumber = 9,
        chapterTitle = "Mechanical Properties of Solids",
        topic = "Hooke's Law & Young's Modulus of Elasticity",
        formula = "Stress = Y · Strain \nY = (F / A) / (ΔL / L) = (F · L) / (A · ΔL)",
        description = "Modulus of elasticity for tensile/compressive deformation within proportional elastic limit.",
        symbolMeanings = "Y = Young's modulus, F = deforming force, A = cross-sectional area, L = original length, ΔL = change in length.",
        siUnit = "N/m² or pascal (Pa)",
        dimensionalFormula = "[M L⁻¹ T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch9_2",
        chapterNumber = 9,
        chapterTitle = "Mechanical Properties of Solids",
        topic = "Bulk Modulus, Shear Modulus & Poisson's Ratio",
        formula = "B = - V · (ΔP / ΔV) \nk = 1/B  (Compressibility) \nη = (F / A) / θ  (Shear Modulus) \nσ = Lateral strain / Longitudinal strain",
        description = "Elastic moduli governing volumetric changes (B), shearing shape change (η), and lateral contraction ratio (σ).",
        symbolMeanings = "B = Bulk modulus, ΔP = excess pressure, ΔV/V = volumetric strain, θ = shear angle, σ = Poisson's ratio.",
        siUnit = "B, η: Pa; k: Pa⁻¹; σ: Dimensionless",
        dimensionalFormula = "B, η: [M L⁻¹ T⁻²]; σ: [M⁰ L⁰ T⁰]",
        examImportance = "Must Know"
    ),
    FormulaItem(
        id = "f_ch9_3",
        chapterNumber = 9,
        chapterTitle = "Mechanical Properties of Solids",
        topic = "Elastic Potential Energy Density in Stretched Wire",
        formula = "u = ½ × Stress × Strain = ½ Y (Strain)² = (Stress)² / (2Y) \nU = u · Volume = ½ F · ΔL",
        description = "Energy stored per unit volume in a strained elastic medium.",
        symbolMeanings = "u = energy density (energy per volume), U = total elastic potential energy, F = stretching force, ΔL = extension.",
        siUnit = "J/m³ (energy density) or J (total energy)",
        dimensionalFormula = "u: [M L⁻¹ T⁻²]",
        examImportance = "Crucial"
    ),

    // -------------------------------------------------------------
    // CHAPTER 10: MECHANICAL PROPERTIES OF FLUIDS
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch10_1",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        topic = "Hydrostatic Pressure & Pascal's Law",
        formula = "P = P₀ + ρ g h \nF₁ / A₁ = F₂ / A₂",
        description = "Total pressure at liquid depth h below open atmospheric surface P₀; hydraulic lift principle.",
        symbolMeanings = "P = absolute pressure, P₀ = atmospheric pressure (1.013×10⁵ Pa), ρ = fluid density, h = liquid column height.",
        siUnit = "pascal (Pa) = N/m²",
        dimensionalFormula = "[M L⁻¹ T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch10_2",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        topic = "Equation of Continuity & Bernoulli's Principle",
        formula = "A₁ v₁ = A₂ v₂ = Constant (Volume flow rate) \nP + ½ ρ v² + ρ g h = Constant",
        description = "Conservation of mass in steady incompressible fluid flow, and conservation of energy per unit volume.",
        symbolMeanings = "A = cross-sectional area, v = fluid flow velocity, P = static pressure, ρ = fluid density, h = elevation.",
        siUnit = "Pressure: Pa",
        dimensionalFormula = "[M L⁻¹ T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch10_3",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        topic = "Torricelli's Law of Efflux",
        formula = "v = √(2 g h)",
        description = "Speed of efflux of liquid emerging through a small orifice situated at depth h below the free liquid surface.",
        symbolMeanings = "v = exit speed of fluid, g = gravity, h = liquid depth above orifice.",
        siUnit = "m/s",
        dimensionalFormula = "[M⁰ L¹ T⁻¹]",
        examImportance = "Must Know"
    ),
    FormulaItem(
        id = "f_ch10_4",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        topic = "Stokes' Law & Terminal Velocity",
        formula = "F_v = 6 π η r v \nv_t = [2 r² (ρ - σ) g] / (9 η)",
        description = "Viscous drag force on a sphere of radius r and its steady terminal velocity falling in a fluid of viscosity η.",
        symbolMeanings = "F_v = viscous force, η = coefficient of viscosity, r = sphere radius, v_t = terminal velocity, ρ = sphere density, σ = medium density.",
        siUnit = "v_t: m/s, F_v: N",
        dimensionalFormula = "v_t: [L T⁻¹], η: [M L⁻¹ T⁻¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch10_5",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        topic = "Surface Tension & Excess Pressure",
        formula = "Liquid Drop: ΔP = 2T / R \nSoap Bubble (2 surfaces): ΔP = 4T / R",
        description = "Excess internal pressure balancing surface tension T across curved liquid interfaces of radius R.",
        symbolMeanings = "ΔP = pressure difference across interface (P_in - P_out), T = surface tension, R = radius of curvature.",
        siUnit = "Pa (N/m²)",
        dimensionalFormula = "[M L⁻¹ T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch10_6",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        topic = "Capillary Rise (Ascent Formula)",
        formula = "h = (2 T cos θ) / (r ρ g)",
        description = "Height h to which a liquid of surface tension T and contact angle θ rises inside a narrow tube of radius r.",
        symbolMeanings = "h = capillary column height, T = surface tension, θ = angle of contact, r = capillary radius, ρ = liquid density, g = 9.8 m/s².",
        siUnit = "meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Crucial"
    ),

    // -------------------------------------------------------------
    // CHAPTER 11: THERMAL PROPERTIES OF MATTER
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch11_1",
        chapterNumber = 11,
        chapterTitle = "Thermal Properties of Matter",
        topic = "Thermal Expansion Coefficients",
        formula = "ΔL = α L ΔT \nΔA = β A ΔT  (β = 2α) \nΔV = γ V ΔT  (γ = 3α)",
        description = "Fractional increase in dimensions per degree Celsius rise in temperature.",
        symbolMeanings = "α = linear expansivity, β = superficial expansivity, γ = volumetric expansivity, ΔT = temperature difference.",
        siUnit = "K⁻¹ or °C⁻¹",
        dimensionalFormula = "[M⁰ L⁰ T⁰ K⁻¹]",
        examImportance = "Must Know"
    ),
    FormulaItem(
        id = "f_ch11_2",
        chapterNumber = 11,
        chapterTitle = "Thermal Properties of Matter",
        topic = "Calorimetry: Specific Heat & Latent Heat",
        formula = "Q = m c ΔT \nQ = m L",
        description = "Thermal energy required to alter temperature by ΔT vs energy required to change phase without temperature change.",
        symbolMeanings = "Q = heat energy transferred, m = mass of substance, c = specific heat capacity, L = latent heat (fusion/vaporization).",
        siUnit = "Q: J; c: J/(kg·K); L: J/kg",
        dimensionalFormula = "c: [L² T⁻² K⁻¹], L: [L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch11_3",
        chapterNumber = 11,
        chapterTitle = "Thermal Properties of Matter",
        topic = "Fourier's Law of Heat Conduction",
        formula = "H = dQ/dt = [K A (T₁ - T₂)] / L",
        description = "Rate of conductive heat flow across a slab of cross-section A, length L, with end temperatures T₁ > T₂.",
        symbolMeanings = "H = rate of heat transfer (thermal current), K = thermal conductivity of material, A = area, L = thickness.",
        siUnit = "watt (W) = J/s",
        dimensionalFormula = "[M L² T⁻³]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch11_4",
        chapterNumber = 11,
        chapterTitle = "Thermal Properties of Matter",
        topic = "Stefan-Boltzmann Law & Wien's Displacement Law",
        formula = "E = σ e A (T⁴ - T₀⁴) \nλ_max · T = b = 2.898 × 10⁻³ m·K",
        description = "Net thermal radiant energy emitted per second by body at temperature T into surroundings at T₀, and wavelength of peak emission.",
        symbolMeanings = "σ = Stefan's constant (5.67×10⁻⁸ W/m²K⁴), e = emissivity (0 ≤ e ≤ 1), A = surface area, b = Wien's constant.",
        siUnit = "E: W, λ_max: m",
        dimensionalFormula = "E: [M L² T⁻³]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch11_5",
        chapterNumber = 11,
        chapterTitle = "Thermal Properties of Matter",
        topic = "Newton's Law of Cooling",
        formula = "dT/dt = - k (T - T₀) \n(T₁ - T₂) / t = K · [(T₁ + T₂)/2 - T₀]",
        description = "Rate of heat loss of a hot body is directly proportional to excess temperature over ambient surroundings for small temperature differences.",
        symbolMeanings = "T = instantaneous body temperature, T₀ = ambient room temperature, k, K = cooling constant, t = time interval.",
        siUnit = "K/s or °C/s",
        dimensionalFormula = "[M⁰ L⁰ T⁻¹ K¹]",
        examImportance = "Must Know"
    ),

    // -------------------------------------------------------------
    // CHAPTER 12: THERMODYNAMICS
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch12_1",
        chapterNumber = 12,
        chapterTitle = "Thermodynamics",
        topic = "First Law of Thermodynamics & Mayer's Formula",
        formula = "ΔQ = ΔU + ΔW \nΔW = P ΔV = ∫ P dV \nC_p - C_v = R",
        description = "Conservation of energy applied to thermodynamic systems; Mayer's relation connects molar heat capacities.",
        symbolMeanings = "ΔQ = heat supplied, ΔU = change in internal energy (n C_v ΔT), ΔW = work done by the system, R = gas constant.",
        siUnit = "joule (J)",
        dimensionalFormula = "[M L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch12_2",
        chapterNumber = 12,
        chapterTitle = "Thermodynamics",
        topic = "Work Done in Isothermal Process",
        formula = "W_iso = n R T ln(V₂ / V₁) = 2.303 n R T log₁₀(P₁ / P₂)",
        description = "Work done during expansion or compression of an ideal gas at constant temperature T (ΔU = 0, ΔQ = ΔW).",
        symbolMeanings = "n = number of moles, R = 8.314 J/mol·K, T = absolute temperature (K), V₁, V₂ = initial and final volumes.",
        siUnit = "joule (J)",
        dimensionalFormula = "[M L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch12_3",
        chapterNumber = 12,
        chapterTitle = "Thermodynamics",
        topic = "Work Done in Adiabatic Process",
        formula = "W_adi = (P₁ V₁ - P₂ V₂) / (γ - 1) = [n R (T₁ - T₂)] / (γ - 1) \nP V^γ = Constant,  T V^(γ-1) = Constant",
        description = "Work done when no heat enters or leaves the system (ΔQ = 0, W = -ΔU).",
        symbolMeanings = "γ = C_p / C_v (atomicity ratio: 1.67 mono, 1.4 dia, 1.33 tri), P₁, V₁, T₁ = initial state, P₂, V₂, T₂ = final state.",
        siUnit = "joule (J)",
        dimensionalFormula = "[M L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch12_4",
        chapterNumber = 12,
        chapterTitle = "Thermodynamics",
        topic = "Efficiency of Carnot Heat Engine & Refrigerator COP",
        formula = "η = 1 - (Q₂ / Q₁) = 1 - (T₂ / T₁) \nβ = Q₂ / W = T₂ / (T₁ - T₂)",
        description = "Maximum theoretical thermal efficiency of a reversible heat engine operating between hot reservoir T₁ and sink T₂.",
        symbolMeanings = "η = Carnot efficiency, T₁ = source temperature (K), T₂ = sink temperature (K), β = coefficient of performance of refrigerator.",
        siUnit = "η: Dimensionless fraction (0 ≤ η < 1)",
        dimensionalFormula = "[M⁰ L⁰ T⁰]",
        examImportance = "Crucial"
    ),

    // -------------------------------------------------------------
    // CHAPTER 13: KINETIC THEORY OF GASES
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch13_1",
        chapterNumber = 13,
        chapterTitle = "Kinetic Theory",
        topic = "Pressure Exerted by an Ideal Gas",
        formula = "P = ⅓ ρ v_rms² = ⅓ (M / V) v_rms² = ⅓ (m N / V) v_rms²",
        description = "Hydrodynamic pressure derived from molecular momentum exchange against container walls.",
        symbolMeanings = "P = gas pressure, ρ = density, v_rms = root mean square molecular speed, m = single molecule mass, N = total molecules.",
        siUnit = "pascal (Pa)",
        dimensionalFormula = "[M L⁻¹ T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch13_2",
        chapterNumber = 13,
        chapterTitle = "Kinetic Theory",
        topic = "RMS Speed & Kinetic Energy per Molecule",
        formula = "v_rms = √(3RT / M) = √(3 k_B T / m) \nE_k = ³⁄₂ k_B T  (per molecule) \nE_total = ³⁄₂ n R T  (for 1 mole of monoatomic gas)",
        description = "Relationship linking microscopic thermal kinetic energy directly to thermodynamic temperature T.",
        symbolMeanings = "R = 8.314 J/mol·K, M = molar mass (kg/mol), k_B = 1.38×10⁻²³ J/K, m = mass of one molecule, T = absolute temperature (K).",
        siUnit = "v_rms: m/s, E_k: J",
        dimensionalFormula = "v_rms: [L T⁻¹], E_k: [M L² T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch13_3",
        chapterNumber = 13,
        chapterTitle = "Kinetic Theory",
        topic = "Degrees of Freedom & Mean Free Path",
        formula = "U = (f/2) n R T \nC_v = (f/2) R,  C_p = (f/2 + 1) R \nλ = 1 / (√2 · π n d²)",
        description = "Equipartition of energy across f degrees of freedom (f=3 mono, f=5 dia); λ is average collision distance.",
        symbolMeanings = "f = degrees of freedom, λ = mean free path, n = molecular number density (molecules/m³), d = molecular diameter.",
        siUnit = "λ: meter (m)",
        dimensionalFormula = "[M⁰ L¹ T⁰]",
        examImportance = "Must Know"
    ),

    // -------------------------------------------------------------
    // CHAPTER 14: OSCILLATIONS
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch14_1",
        chapterNumber = 14,
        chapterTitle = "Oscillations",
        topic = "Simple Harmonic Motion (SHM) Kinematics",
        formula = "x(t) = A cos(ωt + φ₀) \nv(t) = ± ω √(A² - x²)  (v_max = ωA at x=0) \na(t) = - ω² x  (a_max = ω²A at x=±A)",
        description = "Displacement, velocity, and restoring acceleration of a particle executing linear simple harmonic motion.",
        symbolMeanings = "A = amplitude, ω = angular frequency (2π/T), φ₀ = initial phase, x = displacement from equilibrium.",
        siUnit = "x: m, v: m/s, a: m/s²",
        dimensionalFormula = "x: [L], v: [L T⁻¹], a: [L T⁻²]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch14_2",
        chapterNumber = 14,
        chapterTitle = "Oscillations",
        topic = "Time Period of Simple Pendulum",
        formula = "T = 2π √(L / g) \nω = √(g / L)",
        description = "Small-angle oscillation period of a point mass suspended by an inextensible light string of length L.",
        symbolMeanings = "T = oscillation period, L = length of pendulum to center of bob, g = local gravitational acceleration.",
        siUnit = "second (s)",
        dimensionalFormula = "[M⁰ L⁰ T¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch14_3",
        chapterNumber = 14,
        chapterTitle = "Oscillations",
        topic = "Time Period of Loaded Spring (Spring-Mass)",
        formula = "T = 2π √(m / k) \nSeries: 1/k_eq = 1/k₁ + 1/k₂ \nParallel: k_eq = k₁ + k₂",
        description = "Period of vertical or horizontal harmonic oscillation of mass m attached to spring of stiffness k.",
        symbolMeanings = "m = oscillating mass, k = spring constant, k_eq = equivalent stiffness of combination.",
        siUnit = "second (s)",
        dimensionalFormula = "[M⁰ L⁰ T¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch14_4",
        chapterNumber = 14,
        chapterTitle = "Oscillations",
        topic = "Total Mechanical Energy in SHM",
        formula = "K = ½ m ω² (A² - x²) \nU = ½ m ω² x² = ½ k x² \nE_total = K + U = ½ m ω² A² = ½ k A² = Constant",
        description = "Total mechanical energy remains constant throughout oscillation, continuously converting between kinetic and potential forms.",
        symbolMeanings = "K = kinetic energy, U = potential energy, E_total = total energy, A = amplitude, k = mω².",
        siUnit = "joule (J)",
        dimensionalFormula = "[M L² T⁻²]",
        examImportance = "Crucial"
    ),

    // -------------------------------------------------------------
    // CHAPTER 15: WAVES
    // -------------------------------------------------------------
    FormulaItem(
        id = "f_ch15_1",
        chapterNumber = 15,
        chapterTitle = "Waves",
        topic = "Progressive Wave Equation & Wave Speed",
        formula = "y(x,t) = A sin(kx - ωt + φ) \nv = λ / T = f · λ = ω / k",
        description = "General sinusoidal equation for a harmonic wave traveling in the +x direction with wave number k = 2π/λ.",
        symbolMeanings = "A = amplitude, k = propagation constant (rad/m), ω = angular frequency (rad/s), v = wave phase velocity, f = frequency.",
        siUnit = "v: m/s, k: m⁻¹, ω: rad/s",
        dimensionalFormula = "v: [L T⁻¹], k: [L⁻¹], ω: [T⁻¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch15_2",
        chapterNumber = 15,
        chapterTitle = "Waves",
        topic = "Speed of Transverse & Longitudinal Sound Waves",
        formula = "Stretched String: v = √(T_tens / μ) \nSound in Gas (Newton-Laplace): v = √(γ P / ρ) = √(γ R T / M)",
        description = "Wave velocity governed by medium elasticity (tension T_tens or bulk modulus γP) and inertial density (linear mass μ or density ρ).",
        symbolMeanings = "T_tens = string tension (N), μ = mass per unit length (kg/m), γ = adiabatic index (1.4 for air), P = pressure, ρ = density.",
        siUnit = "m/s",
        dimensionalFormula = "[M⁰ L¹ T⁻¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch15_3",
        chapterNumber = 15,
        chapterTitle = "Waves",
        topic = "Standing Waves in Organ Pipes & Stretched Strings",
        formula = "Open Organ Pipe / String: f_n = n · [v / (2L)]  (n = 1, 2, 3...) \nClosed Organ Pipe (one end): f_n = (2n - 1) · [v / (4L)]  (n = 1, 2, 3...)",
        description = "Resonant normal frequencies: Open pipes produce all harmonics (1:2:3:4), while closed pipes produce only odd harmonics (1:3:5).",
        symbolMeanings = "f_n = frequency of n-th harmonic, v = speed of sound in air (≈ 340 m/s), L = length of resonating tube/string.",
        siUnit = "hertz (Hz) = s⁻¹",
        dimensionalFormula = "[M⁰ L⁰ T⁻¹]",
        examImportance = "Crucial"
    ),
    FormulaItem(
        id = "f_ch15_4",
        chapterNumber = 15,
        chapterTitle = "Waves",
        topic = "Beat Frequency & Interference",
        formula = "f_beat = |f₁ - f₂|",
        description = "Periodic waxing and waning in acoustic intensity produced by the superposition of two waves of slightly differing frequencies.",
        symbolMeanings = "f_beat = number of beats heard per second, f₁, f₂ = individual frequencies of oscillating sources.",
        siUnit = "hertz (Hz)",
        dimensionalFormula = "[M⁰ L⁰ T⁻¹]",
        examImportance = "Crucial"
    )
)
