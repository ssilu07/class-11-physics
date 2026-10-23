package com.royals.class11physics

data class DerivationStep(
    val stepNumber: Int,
    val title: String,
    val explanation: String,
    val equation: String
)

data class Derivation(
    val id: String,
    val title: String,
    val chapterNumber: Int,
    val chapterTitle: String,
    val marksWeightage: String,
    val importance: String,
    val statement: String,
    val diagramDescription: String,
    val steps: List<DerivationStep>,
    val finalExpression: String,
    val keyAssumptions: List<String>,
    val examTips: String
)

val topDerivations = listOf(
    // -------------------------------------------------------------
    // 1. PROJECTILE MOTION
    // -------------------------------------------------------------
    Derivation(
        id = "d_projectile",
        title = "Projectile Motion: Trajectory, Time of Flight, Height & Range",
        chapterNumber = 4,
        chapterTitle = "Motion in a Plane",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "A particle is projected from the ground with an initial velocity 'u' at an angle 'θ' with the horizontal under uniform gravitational acceleration 'g'. Derive the equation of its trajectory, time of flight (T), maximum height (H), and horizontal range (R).",
        diagramDescription = "Draw Cartesian axes (X horizontal, Y vertical upwards). Origin O is projection point. Show initial velocity vector 'u' at angle 'θ' with X-axis, resolved into u_x = u cos θ along X and u_y = u sin θ along Y. Show parabolic path reaching vertex (H) and hitting ground at distance R. Label acceleration components: a_x = 0, a_y = -g.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Initial Velocity Components & Equations of Motion",
                explanation = "Resolve the projection velocity u into orthogonal components. Since gravity acts purely downwards, there is zero acceleration along the horizontal direction and constant downward acceleration g along the vertical direction.",
                equation = "u_x = u cos θ,   a_x = 0 \nu_y = u sin θ,   a_y = -g"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Horizontal & Vertical Positions at Time t",
                explanation = "Using kinematic equations of motion for coordinates (x, y) at time t:",
                equation = "x = u_x · t = (u cos θ) · t  ⟹  t = x / (u cos θ) \ny = u_y · t + ½ a_y t² = (u sin θ) · t - ½ g t²"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Equation of Path (Trajectory)",
                explanation = "Substitute the expression for t from the horizontal motion into the vertical displacement equation:",
                equation = "y = (u sin θ) · [x / (u cos θ)] - ½ g · [x / (u cos θ)]² \ny = x tan θ - [g / (2 u² cos² θ)] · x² \nSince this is of the form y = A x - B x², the trajectory is a PARABOLA."
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Time of Flight (T)",
                explanation = "When the projectile returns to the ground, the net vertical displacement y = 0. Set y(T) = 0:",
                equation = "0 = (u sin θ) · T - ½ g T²  ⟹  T · [u sin θ - ½ g T] = 0 \nSince T ≠ 0:  T = (2 u sin θ) / g"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Maximum Height (H)",
                explanation = "At the highest point of trajectory, the vertical component of velocity becomes zero (v_y = 0). Using v_y² = u_y² + 2 a_y H:",
                equation = "0² = (u sin θ)² - 2 g H  ⟹  2 g H = u² sin² θ \nH = (u² sin² θ) / (2 g)"
            ),
            DerivationStep(
                stepNumber = 6,
                title = "Horizontal Range (R)",
                explanation = "Horizontal range is the horizontal distance covered during total time of flight T: R = u_x · T. Using the trigonometric identity 2 sin θ cos θ = sin 2θ:",
                equation = "R = (u cos θ) · [(2 u sin θ) / g] = [u² (2 sin θ cos θ)] / g \nR = (u² sin 2θ) / g \n[Maximum range occurs at θ = 45°: R_max = u² / g]"
            )
        ),
        finalExpression = "Trajectory: y = x tan θ - [g / (2u² cos² θ)] x² \nT = (2u sin θ) / g \nH = (u² sin² θ) / (2g) \nR = (u² sin 2θ) / g",
        keyAssumptions = listOf(
            "Air resistance and atmospheric drag are neglected.",
            "Acceleration due to gravity g is constant in magnitude and direction throughout the motion.",
            "Curvature and rotation of the Earth are ignored."
        ),
        examTips = "Always state why a_x = 0 explicitly in Step 1. In Step 3, clearly conclude that because the equation has degree 2 in x and degree 1 in y, the path is parabolic—examiners award 1 full mark specifically for this conclusion."
    ),

    // -------------------------------------------------------------
    // 2. CENTRIPETAL ACCELERATION
    // -------------------------------------------------------------
    Derivation(
        id = "d_centripetal",
        title = "Centripetal Acceleration (a_c = v² / r)",
        chapterNumber = 4,
        chapterTitle = "Motion in a Plane",
        marksWeightage = "3 Marks (Frequent)",
        importance = "High Yield",
        statement = "Derive an expression for the magnitude and direction of centripetal acceleration for a particle in uniform circular motion of radius 'r' and constant speed 'v'.",
        diagramDescription = "Draw a circle of radius r centered at origin O. Show particle at position P₁ with position vector r₁ and velocity v₁ (tangent to circle). Show position P₂ at small time Δt later with position vector r₂ and velocity v₂. Construct vector triangle showing Δv = v₂ - v₁. Note that the position triangle OP₁P₂ and the velocity triangle are similar isosceles triangles.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Geometry of Position and Velocity Vectors",
                explanation = "In uniform circular motion, speed is constant (|v₁| = |v₂| = v) and radius is constant (|r₁| = |r₂| = r). In time interval Δt, the radius vector sweeps an angle Δθ, and the velocity vector turns through the same angle Δθ because velocity is always perpendicular to radius.",
                equation = "|r₁| = |r₂| = r,   |v₁| = |v₂| = v,   Δθ = (arc P₁P₂) / r ≈ |Δr| / r"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Similar Triangles Relation",
                explanation = "The triangle formed by position vectors Δr = r₂ - r₁ and the triangle formed by velocity vectors Δv = v₂ - v₁ are similar isosceles triangles with vertex angle Δθ. Therefore, the ratios of base to side are equal:",
                equation = "|Δv| / v = |Δr| / r  ⟹  |Δv| = (v / r) · |Δr|"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Taking the Limit as Δt ⟶ 0",
                explanation = "Divide both sides by Δt and take the limit as Δt approaches zero to find instantaneous acceleration magnitude:",
                equation = "a = lim_{Δt ⟶ 0} (|Δv| / Δt) = (v / r) · lim_{Δt ⟶ 0} (|Δr| / Δt) \nSince lim_{Δt ⟶ 0} (|Δr| / Δt) = v (instantaneous speed): \na_c = (v / r) · v = v² / r"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Angular Velocity Relation and Direction",
                explanation = "Substitute linear speed in terms of angular velocity (v = ω r):",
                equation = "a_c = (ω r)² / r = ω² r \nDirection: As Δt ⟶ 0, vector Δv becomes perpendicular to velocity vector v, pointing radially inwards towards the center O."
            )
        ),
        finalExpression = "a_c = v² / r = ω² r  (directed radially inwards)",
        keyAssumptions = listOf(
            "Speed v is constant (uniform circular motion, tangential acceleration a_t = 0).",
            "Radius r of circular trajectory is strictly fixed."
        ),
        examTips = "Clearly state that both triangles are similar because both are isosceles and have the same apex angle Δθ. Mentioning the inward radial direction secures the final mark."
    ),

    // -------------------------------------------------------------
    // 3. BANKING OF ROADS
    // -------------------------------------------------------------
    Derivation(
        id = "d_banking",
        title = "Banking of Curved Roads (With & Without Friction)",
        chapterNumber = 5,
        chapterTitle = "Laws of Motion",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "Obtain an expression for the optimum speed and maximum safe speed of a vehicle of mass 'm' navigating a curved banked road of radius 'r' at an inclination 'θ' with coefficient of friction 'μ_s'.",
        diagramDescription = "Draw an inclined plane at angle θ with horizontal. Place vehicle as a block on incline. Forces acting: (1) Weight mg vertically downwards, (2) Normal reaction N perpendicular to inclined surface, (3) Limiting friction f_s directed downwards along the incline (preventing outward skidding). Resolve N into N cos θ (vertical) and N sin θ (horizontal inwards). Resolve f_s into f_s sin θ (vertical downwards) and f_s cos θ (horizontal inwards).",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Case 1: Frictionless Road (Optimum Speed)",
                explanation = "If there is no friction (μ_s = 0), the horizontal component of normal reaction provides the entire necessary centripetal force, while the vertical component balances the vehicle weight:",
                equation = "N cos θ = m g   --- (Eq. 1) \nN sin θ = (m v₀²) / r   --- (Eq. 2)"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Optimum Speed Formula",
                explanation = "Dividing Eq. 2 by Eq. 1 eliminates N and m:",
                equation = "(N sin θ) / (N cos θ) = [(m v₀²) / r] / (m g) \ntan θ = v₀² / (r g)  ⟹  v₀ = √(r g tan θ)"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Case 2: Rough Banked Road (Maximum Safe Speed v_max)",
                explanation = "At maximum speed before skidding outwards, limiting static friction f_s = μ_s N acts down the incline. Balancing vertical forces (equilibrium along Y-axis):",
                equation = "N cos θ = m g + f_s sin θ \nSince f_s = μ_s N: \nN cos θ - μ_s N sin θ = m g  ⟹  N (cos θ - μ_s sin θ) = m g   --- (Eq. 3)"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Centripetal Force Balance (Along Horizontal X-axis)",
                explanation = "Both horizontal components of normal reaction and friction act towards the center of curvature, providing the required centripetal force:",
                equation = "N sin θ + f_s cos θ = (m v_max²) / r \nN (sin θ + μ_s cos θ) = (m v_max²) / r   --- (Eq. 4)"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Dividing Equations for Maximum Safe Speed",
                explanation = "Dividing Eq. 4 by Eq. 3:",
                equation = "[N (sin θ + μ_s cos θ)] / [N (cos θ - μ_s sin θ)] = [(m v_max²) / r] / (m g) \n(sin θ + μ_s cos θ) / (cos θ - μ_s sin θ) = v_max² / (r g) \nDivide numerator and denominator of LHS by cos θ: \n(tan θ + μ_s) / (1 - μ_s tan θ) = v_max² / (r g)"
            ),
            DerivationStep(
                stepNumber = 6,
                title = "Final Expression for v_max",
                explanation = "Rearranging gives the maximum safe speed without skidding:",
                equation = "v_max = √[ r g · (μ_s + tan θ) / (1 - μ_s tan θ) ]"
            )
        ),
        finalExpression = "Optimum: v₀ = √(r g tan θ) \nMaximum: v_max = √[ r g (μ_s + tan θ) / (1 - μ_s tan θ) ]",
        keyAssumptions = listOf(
            "Vehicle behaves as a point mass without toppling.",
            "Road is banked uniformly at angle θ along a circular arc of radius r.",
            "Limiting friction f_s = μ_s N is fully mobilized."
        ),
        examTips = "Draw the free body diagram with crisp arrows. Remember that dividing the numerator and denominator by cos θ is the key algebraic trick to arrive at the tan θ form."
    ),

    // -------------------------------------------------------------
    // 4. WORK-ENERGY THEOREM
    // -------------------------------------------------------------
    Derivation(
        id = "d_work_energy",
        title = "Work-Energy Theorem for a Variable Force",
        chapterNumber = 6,
        chapterTitle = "Work, Energy and Power",
        marksWeightage = "3 Marks / 5 Marks",
        importance = "Must Know",
        statement = "State and prove the Work-Energy Theorem for a body of mass 'm' acted upon by a one-dimensional variable force F(x).",
        diagramDescription = "Show a particle of mass m moving along the X-axis from initial position x_i (velocity u) to final position x_f (velocity v) under variable force F(x). Plot an arbitrary F vs x curve and shade the element of width dx with height F.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Statement of the Theorem",
                explanation = "The work done by the net force acting on a body equals the net change in its kinetic energy:",
                equation = "W_net = ΔK = K_f - K_i = ½ m v² - ½ m u²"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Rate of Change of Kinetic Energy",
                explanation = "Define kinetic energy K = ½ m v². Differentiate K with respect to time t:",
                equation = "dK/dt = d/dt (½ m v²) = ½ m · (2v · dv/dt) = m v · (dv/dt)"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Connecting with Force and Displacement",
                explanation = "By Newton's second law, acceleration a = dv/dt and net force F = m a. Furthermore, velocity v = dx/dt:",
                equation = "dK/dt = (m · dv/dt) · v = F · (dx/dt) \ndK = F dx"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Integrating from Initial to Final State",
                explanation = "Integrate both sides from initial state (x_i, u) to final state (x_f, v):",
                equation = "∫_{K_i}^{K_f} dK = ∫_{x_i}^{x_f} F dx \n[K]_{K_i}^{K_f} = W \nK_f - K_i = W  ⟹  W = ½ m v² - ½ m u²"
            )
        ),
        finalExpression = "W_net = ΔK = ½ m v² - ½ m u²",
        keyAssumptions = listOf(
            "Motion is rectilinear (one-dimensional).",
            "Body is treated as a rigid particle (no internal vibrational deformation).",
            "Mass m remains constant during motion."
        ),
        examTips = "The calculus step dK/dt = m v (dv/dt) = F (dx/dt) is the heart of this proof. Writing this identity correctly yields immediate full marks."
    ),

    // -------------------------------------------------------------
    // 5. ELASTIC COLLISION IN 1D
    // -------------------------------------------------------------
    Derivation(
        id = "d_elastic_collision",
        title = "One-Dimensional Elastic Collision (Final Velocities)",
        chapterNumber = 6,
        chapterTitle = "Work, Energy and Power",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "Two masses m₁ and m₂ moving along the same straight line with initial velocities u₁ and u₂ undergo a head-on perfectly elastic collision. Derive expressions for their final velocities v₁ and v₂ after the collision.",
        diagramDescription = "Draw three snapshots along a horizontal line: (1) Before collision: m₁ with velocity u₁ pursuing m₂ with velocity u₂ (u₁ > u₂), (2) During collision: both spheres in physical contact, (3) After collision: m₁ with velocity v₁ and m₂ with velocity v₂.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Conservation of Linear Momentum",
                explanation = "Since there is no external horizontal force, total linear momentum is conserved:",
                equation = "m₁ u₁ + m₂ u₂ = m₁ v₁ + m₂ v₂ \nm₁ (u₁ - v₁) = m₂ (v₂ - u₂)   --- (Eq. 1)"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Conservation of Kinetic Energy",
                explanation = "In a perfectly elastic collision, total kinetic energy is also conserved:",
                equation = "½ m₁ u₁² + ½ m₂ u₂² = ½ m₁ v₁² + ½ m₂ v₂² \nm₁ (u₁² - v₁²) = m₂ (v₂² - u₂²) \nm₁ (u₁ - v₁)(u₁ + v₁) = m₂ (v₂ - u₂)(v₂ + u₂)   --- (Eq. 2)"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Relative Velocity of Approach Equals Relative Velocity of Separation",
                explanation = "Dividing Eq. 2 by Eq. 1 eliminates masses:",
                equation = "u₁ + v₁ = v₂ + u₂ \nu₁ - u₂ = v₂ - v₁ \n(Relative velocity of approach = Relative velocity of separation)"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Finding Velocity v₁ of First Body",
                explanation = "From the separation relation, express v₂ = u₁ - u₂ + v₁ and substitute into Eq. 1:",
                equation = "m₁ u₁ + m₂ u₂ = m₁ v₁ + m₂ (u₁ - u₂ + v₁) \nm₁ u₁ + m₂ u₂ = m₁ v₁ + m₂ u₁ - m₂ u₂ + m₂ v₁ \n(m₁ - m₂) u₁ + 2 m₂ u₂ = (m₁ + m₂) v₁ \nv₁ = [(m₁ - m₂) / (m₁ + m₂)] u₁ + [2 m₂ / (m₁ + m₂)] u₂"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Finding Velocity v₂ of Second Body",
                explanation = "Similarly, substituting v₁ = v₂ - (u₁ - u₂) into Eq. 1:",
                equation = "v₂ = [2 m₁ / (m₁ + m₂)] u₁ + [(m₂ - m₁) / (m₁ + m₂)] u₂"
            )
        ),
        finalExpression = "v₁ = [(m₁ - m₂)/(m₁ + m₂)] u₁ + [2m₂/(m₁ + m₂)] u₂ \nv₂ = [2m₁/(m₁ + m₂)] u₁ + [(m₂ - m₁)/(m₁ + m₂)] u₂",
        keyAssumptions = listOf(
            "Collision is strictly head-on (one-dimensional along the line of centers).",
            "No mechanical energy is lost to heat, sound, or permanent deformation (elasticity e = 1)."
        ),
        examTips = "Mention the special case: when m₁ = m₂, v₁ = u₂ and v₂ = u₁ (velocities simply exchange). CBSE papers frequently ask this 1-mark deduction at the end."
    ),

    // -------------------------------------------------------------
    // 6. VARIATION OF G WITH ALTITUDE AND DEPTH
    // -------------------------------------------------------------
    Derivation(
        id = "d_grav_altitude_depth",
        title = "Variation of Acceleration Due to Gravity (Altitude & Depth)",
        chapterNumber = 8,
        chapterTitle = "Gravitation",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "Derive expressions for the variation of acceleration due to gravity 'g' with: (i) altitude 'h' above the Earth's surface, and (ii) depth 'd' below the Earth's surface.",
        diagramDescription = "Draw a sphere of radius R representing Earth with mass M and center O. (i) Mark point P at height h above surface at distance (R + h) from center. (ii) Draw inner concentric sphere of radius (R - d) at depth d below surface; shade the inner core.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Surface Value of g",
                explanation = "On the Earth's surface at distance R from the center:",
                equation = "g = (G M) / R²   --- (Eq. 1)"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Part (i): Variation with Altitude (Height h)",
                explanation = "At height h above surface, distance from center is (R + h). Acceleration due to gravity is:",
                equation = "g_h = (G M) / (R + h)² = (G M) / [R² (1 + h/R)²] = [G M / R²] · (1 + h/R)⁻² \ng_h = g · (1 + h/R)⁻²"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Binomial Approximation for Small Altitudes (h ≪ R)",
                explanation = "Expanding binomially (1 + x)ⁿ ≈ 1 + n x when x ≪ 1:",
                equation = "g_h ≈ g · (1 - 2h/R) \n[Conclusion: g decreases linearly with altitude for h ≪ R]"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Part (ii): Variation with Depth d",
                explanation = "At depth d, distance from Earth's center is r = (R - d). By Gauss's law / shell theorem, the outer spherical shell of thickness d exerts zero net gravitational force on the body. Only the inner sphere of radius (R - d) and mass M' contributes:",
                equation = "Assuming uniform density ρ: M = ⁴⁄₃ π R³ ρ \nM' = ⁴⁄₃ π (R - d)³ ρ = M · [(R - d)³ / R³]"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Computing g_d at Depth d",
                explanation = "The gravitational acceleration at distance (R - d) from the center is:",
                equation = "g_d = (G M') / (R - d)² = G · [M (R - d)³ / R³] / (R - d)² \ng_d = (G M / R³) · (R - d) = [G M / R²] · [(R - d) / R] \ng_d = g · (1 - d/R)"
            ),
            DerivationStep(
                stepNumber = 6,
                title = "Comparison at Earth's Center",
                explanation = "At the center of Earth, depth d = R, therefore:",
                equation = "g_center = g · (1 - R/R) = 0"
            )
        ),
        finalExpression = "Altitude: g_h ≈ g (1 - 2h/R)  [for h ≪ R] \nDepth: g_d = g (1 - d/R)  [exact at all depths]",
        keyAssumptions = listOf(
            "Earth is assumed to be a uniform homogeneous sphere of constant density ρ.",
            "For altitude approximation, h must be very small compared to R (6400 km)."
        ),
        examTips = "Note that the depth formula g_d = g(1 - d/R) is EXACT for all depths under uniform density, while g_h = g(1 - 2h/R) is an APPROXIMATION valid only for h ≪ R. Highlighting this difference earns full credit."
    ),

    // -------------------------------------------------------------
    // 7. ESCAPE VELOCITY
    // -------------------------------------------------------------
    Derivation(
        id = "d_escape_velocity",
        title = "Escape Velocity from Earth's Surface",
        chapterNumber = 8,
        chapterTitle = "Gravitation",
        marksWeightage = "3 Marks / 5 Marks",
        importance = "High Yield",
        statement = "Define escape velocity. Derive an expression for the minimum escape speed required by a body of mass 'm' to escape the gravitational attraction of Earth of mass 'M' and radius 'R'.",
        diagramDescription = "Draw Earth of mass M, radius R with center O. Show a body of mass m projected vertically from surface with speed v_e. At an intermediate distance r from center O, mark displacement element dr along radial line. Show path extending to infinity where total mechanical energy E = 0.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Definition & Method of Work Integration",
                explanation = "Escape velocity is the minimum speed with which a body must be projected from Earth's surface so that it just overcomes Earth's gravitational pull and never returns. Consider mass m at distance r from center O. Gravitational force:",
                equation = "F(r) = (G M m) / r²"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Work Done against Gravitational Attraction to Infinity",
                explanation = "Work done in moving the body by an infinitesimal distance dr radially outwards against gravitational pull is dW = F dr. Total work to reach infinity (r = ∞) from surface (r = R):",
                equation = "W = ∫_R^∞ F dr = ∫_R^∞ (G M m / r²) dr \nW = G M m · [- 1/r]_R^∞ = G M m · [0 - (-1/R)] = (G M m) / R"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Equating Work to Initial Kinetic Energy",
                explanation = "This work must be supplied by the initial kinetic energy ½ m v_e² imparted at launch:",
                equation = "½ m v_e² = W = (G M m) / R \nv_e² = (2 G M) / R  ⟹  v_e = √(2 G M / R)"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Expressing in terms of Surface Gravity g",
                explanation = "Substitute G M = g R² (since g = G M / R²):",
                equation = "v_e = √[ 2 (g R²) / R ] = √(2 g R) \nNumerical value on Earth (g = 9.8 m/s², R = 6.4 × 10⁶ m): \nv_e = √(2 × 9.8 × 6.4 × 10⁶) ≈ 11.2 × 10³ m/s = 11.2 km/s"
            )
        ),
        finalExpression = "v_e = √(2GM / R) = √(2 g R) ≈ 11.2 km/s",
        keyAssumptions = listOf(
            "Air resistance of atmosphere is neglected.",
            "Gravitational influence of Moon, Sun, and other celestial bodies is neglected."
        ),
        examTips = "State clearly that escape speed does NOT depend on the mass of the projected body or the angle of projection! It is purely a property of the planet."
    ),

    // -------------------------------------------------------------
    // 8. TERMINAL VELOCITY & STOKES' LAW
    // -------------------------------------------------------------
    Derivation(
        id = "d_terminal_velocity",
        title = "Terminal Velocity of a Spherical Body in a Viscous Medium",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        marksWeightage = "3 Marks / 5 Marks",
        importance = "Frequent 5-Marker",
        statement = "Derive an expression for the terminal velocity of a small spherical ball of radius 'r' and density 'ρ' falling freely through a viscous fluid of density 'σ' and coefficient of viscosity 'η'.",
        diagramDescription = "Draw a tall vertical cylinder filled with viscous liquid of density σ and viscosity η. Show sphere of radius r, density ρ falling vertically downwards. Draw three force vectors: (1) Downward gravitational force W = mg, (2) Upward buoyant force F_b, (3) Upward viscous drag force F_v = 6 π η r v. Indicate terminal velocity v_t when net acceleration a = 0.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Forces Acting on Falling Sphere",
                explanation = "As the sphere falls through the viscous liquid, three distinct forces act on it: (1) True weight downwards, (2) Upward buoyant thrust (Archimedes principle), (3) Upward viscous drag force (Stokes' Law):",
                equation = "1. Weight: W = m g = (⁴⁄₃ π r³ ρ) g  (downwards) \n2. Buoyant Force: F_b = (⁴⁄₃ π r³ σ) g  (upwards) \n3. Viscous Drag: F_v = 6 π η r v  (upwards)"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Condition for Terminal Velocity",
                explanation = "As velocity increases, viscous drag increases until the downward weight is exactly balanced by the sum of buoyant thrust and viscous drag. Net force becomes zero and acceleration vanishes (a = 0):",
                equation = "F_net = W - F_b - F_v = 0 \nW = F_b + F_v"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Equating Forces and Solving for v_t",
                explanation = "Substitute the algebraic expressions into the equilibrium equation:",
                equation = "(⁴⁄₃ π r³ ρ) g = (⁴⁄₃ π r³ σ) g + 6 π η r v_t \n6 π η r v_t = ⁴⁄₃ π r³ (ρ - σ) g \nv_t = [⁴⁄₃ π r³ (ρ - σ) g] / (6 π η r)"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Simplification to Final Formula",
                explanation = "Cancel common factor of 2 π r from numerator and denominator:",
                equation = "v_t = [2 r² (ρ - σ) g] / (9 η)"
            )
        ),
        finalExpression = "v_t = [2 r² (ρ - σ) g] / (9 η)",
        keyAssumptions = listOf(
            "The fluid is infinite in extent and free of boundary wall effects.",
            "The falling body is a perfectly smooth and rigid sphere.",
            "Flow remains strictly streamlined (laminar, low Reynolds number)."
        ),
        examTips = "Note that if body density ρ > σ, v_t is positive (body falls, e.g. steel ball in glycerin). If ρ < σ, v_t is negative (body rises, e.g. air bubble in water). Writing this physical insight scores brownie points."
    ),

    // -------------------------------------------------------------
    // 9. BERNOULLI'S THEOREM
    // -------------------------------------------------------------
    Derivation(
        id = "d_bernoulli",
        title = "Bernoulli's Equation (Work-Energy Principle in Fluid Flow)",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "State and prove Bernoulli's Theorem for the steady, streamline flow of an ideal, incompressible, non-viscous fluid through a tube of varying cross-section and height.",
        diagramDescription = "Draw a pipe of non-uniform cross-section and height. Section 1 on left has cross-sectional area A₁, height h₁, pressure P₁, velocity v₁. Section 2 on right has cross-sectional area A₂, height h₂, pressure P₂, velocity v₂. In small time interval Δt, liquid at section 1 moves distance Δx₁ = v₁ Δt, while liquid at section 2 moves distance Δx₂ = v₂ Δt. Label shaded volume elements ΔV = A₁ Δx₁ = A₂ Δx₂.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Statement & Equation of Continuity",
                explanation = "For an ideal, incompressible, non-viscous fluid in streamline flow, the sum of pressure energy, kinetic energy, and potential energy per unit volume is constant throughout the stream. By continuity, mass entering in time Δt equals mass leaving:",
                equation = "Δm = ρ A₁ v₁ Δt = ρ A₂ v₂ Δt = ρ ΔV  ⟹  A₁ v₁ Δt = A₂ v₂ Δt = ΔV"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Net Work Done by Pressure Forces",
                explanation = "Work done on fluid at Section 1 by pressure P₁ is W₁ = F₁ Δx₁ = (P₁ A₁) (v₁ Δt) = P₁ ΔV. Work done by fluid at Section 2 against pressure P₂ is W₂ = - (P₂ A₂) (v₂ Δt) = - P₂ ΔV. Net work done:",
                equation = "W_net = W₁ + W₂ = P₁ ΔV - P₂ ΔV = (P₁ - P₂) ΔV"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Change in Kinetic Energy and Potential Energy",
                explanation = "In time Δt, mass Δm is transferred from velocity v₁ and height h₁ to velocity v₂ and height h₂:",
                equation = "ΔK = ½ Δm v₂² - ½ Δm v₁² = ½ (ρ ΔV) (v₂² - v₁²) \nΔU = Δm g h₂ - Δm g h₁ = (ρ ΔV) g (h₂ - h₁)"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Applying the Work-Energy Theorem",
                explanation = "By Work-Energy Theorem, W_net = ΔK + ΔU:",
                equation = "(P₁ - P₂) ΔV = ½ ρ ΔV (v₂² - v₁²) + ρ g ΔV (h₂ - h₁) \nCancel common factor ΔV: \nP₁ - P₂ = ½ ρ v₂² - ½ ρ v₁² + ρ g h₂ - ρ g h₁ \nP₁ + ½ ρ v₁² + ρ g h₁ = P₂ + ½ ρ v₂² + ρ g h₂"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "General Expression",
                explanation = "Since sections 1 and 2 are arbitrary points along the streamline:",
                equation = "P + ½ ρ v² + ρ g h = Constant"
            )
        ),
        finalExpression = "P + ½ ρ v² + ρ g h = Constant  (or  P/(ρg) + v²/(2g) + h = Constant)",
        keyAssumptions = listOf(
            "Fluid is non-viscous (zero internal fluid friction).",
            "Fluid is incompressible (density ρ is constant).",
            "Flow is steady and streamline (no turbulence or eddies).",
            "Flow is irrotational."
        ),
        examTips = "Be sure to list the four ideal fluid conditions (non-viscous, incompressible, steady, irrotational) in the introduction. Also mention the head form: Pressure head P/(ρg) + Velocity head v²/(2g) + Elevation head h = Constant."
    ),

    // -------------------------------------------------------------
    // 10. TIME PERIOD OF SIMPLE PENDULUM
    // -------------------------------------------------------------
    Derivation(
        id = "d_pendulum",
        title = "Time Period of a Simple Pendulum",
        chapterNumber = 14,
        chapterTitle = "Oscillations",
        marksWeightage = "3 Marks / 5 Marks",
        importance = "Frequent 5-Marker",
        statement = "Show that the oscillations of a simple pendulum are simple harmonic for small angular displacements, and derive an expression for its time period 'T'.",
        diagramDescription = "Draw rigid support S from which a light inextensible string of length L suspends a heavy bob of mass m. Displace bob through small angle θ from mean vertical position. Forces on bob: (1) Tension T_s along string towards support S, (2) Weight mg vertically downwards. Resolve mg into mg cos θ (balancing tension) and mg sin θ (tangential restoring force). Mark arc displacement x = L θ.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Resolution of Forces on Displaced Bob",
                explanation = "When displaced through angle θ from vertical, forces acting on the bob are weight mg and string tension T_s. Resolving weight into components:",
                equation = "Radial component: mg cos θ (balanced by tension T_s) \nTangential restoring force: F_restoring = - mg sin θ"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Small Angle Approximation (SHM Condition)",
                explanation = "For small oscillations (θ < 10° in radians), sin θ ≈ θ = x / L, where x is the linear displacement along the arc:",
                equation = "F_restoring = - mg θ = - mg (x / L) = - (mg / L) · x"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Proving Motion is Simple Harmonic",
                explanation = "Since restoring force is directly proportional to displacement and directed towards the mean position (F ∝ -x), the motion is Simple Harmonic Motion (SHM):",
                equation = "a = F / m = - (g / L) · x \nComparing with standard SHM equation a = - ω² x: \nω² = g / L  ⟹  ω = √(g / L)"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Calculating Time Period T and Frequency",
                explanation = "The time period of oscillation is T = 2π / ω:",
                equation = "T = 2π / √(g / L) = 2π √(L / g) \nFrequency f = 1 / T = (1 / 2π) √(g / L)"
            )
        ),
        finalExpression = "T = 2π √(L / g)",
        keyAssumptions = listOf(
            "Amplitude of oscillation is sufficiently small (sin θ ≈ θ in radians).",
            "String is light (massless) and inextensible.",
            "Bob is treated as a point mass, and air drag is negligible."
        ),
        examTips = "Emphasize that the time period T is independent of the mass of the bob and independent of amplitude (isochronism), depending solely on effective length L and local gravity g."
    ),

    // -------------------------------------------------------------
    // 11. WORK DONE IN ISOTHERMAL & ADIABATIC PROCESSES
    // -------------------------------------------------------------
    Derivation(
        id = "d_isothermal_adiabatic",
        title = "Work Done in Isothermal & Adiabatic Expansions",
        chapterNumber = 12,
        chapterTitle = "Thermodynamics",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "Derive analytical expressions for the work done by an ideal gas (n moles) during: (i) an Isothermal expansion from (P₁, V₁) to (P₂, V₂), and (ii) an Adiabatic expansion from (P₁, V₁, T₁) to (P₂, V₂, T₂).",
        diagramDescription = "Draw P-V indicator diagrams: (i) Isothermal curve (P V = const) showing hyperbolic decrease from (P₁, V₁) to (P₂, V₂). Shade area under curve W = ∫ P dV. (ii) Adiabatic curve (P V^γ = const) which is steeper than the isothermal curve, expanding from (P₁, V₁, T₁) to (P₂, V₂, T₂).",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Part (i): Work Done in Isothermal Expansion",
                explanation = "In an isothermal process, temperature T remains strictly constant. For n moles of an ideal gas, P V = n R T, so P = n R T / V:",
                equation = "W_iso = ∫_{V₁}^{V₂} P dV = ∫_{V₁}^{V₂} (n R T / V) dV \nW_iso = n R T ∫_{V₁}^{V₂} (1 / V) dV = n R T · [ln V]_{V₁}^{V₂} \nW_iso = n R T ln(V₂ / V₁) = 2.303 n R T log₁₀(V₂ / V₁)"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Isothermal Work in terms of Pressure",
                explanation = "By Boyle's Law at constant T, P₁ V₁ = P₂ V₂, so V₂ / V₁ = P₁ / P₂:",
                equation = "W_iso = 2.303 n R T log₁₀(P₁ / P₂)"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Part (ii): Work Done in Adiabatic Expansion",
                explanation = "In an adiabatic process, heat exchange ΔQ = 0. The gas follows Poisson's relation P V^γ = K (constant), so P = K · V⁻ᵞ:",
                equation = "W_adi = ∫_{V₁}^{V₂} P dV = K ∫_{V₁}^{V₂} V⁻ᵞ dV \nW_adi = K · [ V^(1 - γ) / (1 - γ) ]_{V₁}^{V₂} = [K / (1 - γ)] · [V₂^(1 - γ) - V₁^(1 - γ)]"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Expressing in terms of Initial and Final States",
                explanation = "Since K = P₁ V₁^γ = P₂ V₂^γ:",
                equation = "W_adi = [1 / (1 - γ)] · [P₂ V₂^γ · V₂^(1 - γ) - P₁ V₁^γ · V₁^(1 - γ)] \nW_adi = [1 / (1 - γ)] · [P₂ V₂ - P₁ V₁] \nW_adi = (P₁ V₁ - P₂ V₂) / (γ - 1)"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Expressing in terms of Temperature",
                explanation = "Using ideal gas equation P₁ V₁ = n R T₁ and P₂ V₂ = n R T₂:",
                equation = "W_adi = [n R (T₁ - T₂)] / (γ - 1)"
            )
        ),
        finalExpression = "Isothermal: W = n R T ln(V₂ / V₁) = 2.303 n R T log₁₀(P₁ / P₂) \nAdiabatic: W = (P₁ V₁ - P₂ V₂) / (γ - 1) = [n R (T₁ - T₂)] / (γ - 1)",
        keyAssumptions = listOf(
            "Gas obeys ideal gas equation PV = nRT.",
            "Process is quasi-static and reversible.",
            "In adiabatic expansion, container walls are completely insulated thermally (dQ = 0)."
        ),
        examTips = "Note that in adiabatic expansion, temperature falls (T₂ < T₁), so work done is positive as internal energy decreases. Stating W_adi = -ΔU = -n C_v ΔT verifies the result and impresses examiners."
    ),

    // -------------------------------------------------------------
    // 12. PRESSURE OF AN IDEAL GAS
    // -------------------------------------------------------------
    Derivation(
        id = "d_ideal_gas_pressure",
        title = "Pressure Exerted by an Ideal Gas (P = ⅓ ρ v_rms²)",
        chapterNumber = 13,
        chapterTitle = "Kinetic Theory",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "Using kinetic theory of gases, derive an expression for the pressure exerted by an ideal gas on the walls of a cubical container of volume V.",
        diagramDescription = "Draw a cube of side length L and volume V = L³. Align along X, Y, Z axes. Show a molecule of mass m moving with velocity vector (v_x1, v_y1, v_z1) colliding elastically with the shaded planar wall A₁ perpendicular to the X-axis at x = L.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Momentum Change in Single Collision with Wall",
                explanation = "Consider a molecule of mass m moving towards the right wall with velocity component v_x. The collision with the wall is assumed to be perfectly elastic, so the molecule rebounds with velocity -v_x while v_y and v_z remain unaffected:",
                equation = "Initial momentum along X = m v_x \nFinal momentum along X = - m v_x \nChange in momentum Δp_x = Final - Initial = -2 m v_x \nMomentum transferred to wall = + 2 m v_x"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Time Between Successive Collisions",
                explanation = "The molecule travels distance 2L back and forth along the X-axis before striking the same wall again:",
                equation = "Δt = 2L / v_x"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Force Exerted by Single Molecule",
                explanation = "By Newton's second law, rate of momentum transfer equals force exerted on the wall by molecule 1:",
                equation = "F₁ = Δp / Δt = (2 m v_{x1}) / (2L / v_{x1}) = (m v_{x1}²) / L"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Total Force from All N Molecules",
                explanation = "Summing the forces from all N molecules in the container:",
                equation = "F_total = (m / L) · (v_{x1}² + v_{x2}² + ... + v_{xN}²) = (m N / L) · ⟨v_x²⟩"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Pressure on the Wall & Isotropic Speed Distribution",
                explanation = "Pressure P = F_total / Area = F_total / L². Since Volume V = L³: P = (m N / V) · ⟨v_x²⟩. Since molecular motion is completely isotropic (no preferred direction):",
                equation = "⟨v²⟩ = ⟨v_x²⟩ + ⟨v_y²⟩ + ⟨v_z²⟩ = 3 ⟨v_x²⟩  ⟹  ⟨v_x²⟩ = ⅓ ⟨v²⟩ = ⅓ v_rms² \nP = ⅓ (m N / V) v_rms² = ⅓ (M / V) v_rms² = ⅓ ρ v_rms²"
            )
        ),
        finalExpression = "P = ⅓ ρ v_rms² = ⅓ (M / V) v_rms²",
        keyAssumptions = listOf(
            "Molecules are point masses undergoing perfectly elastic collisions with container walls.",
            "Intermolecular forces between molecules are negligible except during brief collisions.",
            "Molecular motion is completely random and isotropic."
        ),
        examTips = "Remember to define root mean square speed v_rms² = ⟨v²⟩. Show that ⟨v_x²⟩ = ⅓ v_rms² explicitly because the x, y, and z directions are statistically equivalent."
    ),

    // -------------------------------------------------------------
    // 13. ROLLING MOTION DOWN AN INCLINED PLANE
    // -------------------------------------------------------------
    Derivation(
        id = "d_rolling_acceleration",
        title = "Acceleration of a Rolling Body Down an Inclined Plane",
        chapterNumber = 7,
        chapterTitle = "System of Particles and Rotational Motion",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "A round symmetrical body of mass 'M', radius 'R', and radius of gyration 'k' rolls down an inclined plane of inclination 'θ' without slipping. Derive expressions for its linear acceleration 'a', linear velocity 'v' at the bottom of incline of height 'h', and minimum coefficient of static friction 'μ_min' required for pure rolling.",
        diagramDescription = "Draw an inclined plane at angle θ with horizontal. Show a circular cross-section body of mass M, radius R resting on the incline. Mark forces: (1) Weight Mg vertically downwards, resolved into Mg sin θ down the incline and Mg cos θ perpendicular to incline. (2) Normal reaction N perpendicular to incline upwards. (3) Static friction force f_s directed upwards along incline at contact point. Mark linear acceleration 'a' down incline and angular acceleration 'α' clockwise.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Translational Equation of Motion Along Incline",
                explanation = "Applying Newton's second law along the direction of the inclined plane:",
                equation = "M g sin θ - f_s = M a   --- (Eq. 1)"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Rotational Equation of Motion About Center of Mass",
                explanation = "The normal force and gravity pass through the center of mass, producing zero torque. The only force exerting torque about the center of mass is static friction f_s with moment arm R:",
                equation = "τ = f_s · R = I α   --- (Eq. 2)"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Pure Rolling Condition and Moment of Inertia",
                explanation = "For rolling without slipping, acceleration of the contact point is zero, giving a = α R ⟹ α = a / R. Expressing moment of inertia in terms of radius of gyration k (I = M k²):",
                equation = "f_s · R = (M k²) · (a / R)  ⟹  f_s = M a (k² / R²)"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Solving for Linear Acceleration a",
                explanation = "Substitute the expression for f_s into Eq. 1:",
                equation = "M g sin θ - M a (k² / R²) = M a \nM g sin θ = M a · [1 + k² / R²] \na = (g sin θ) / [1 + k² / R²]"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Velocity at the Bottom of Incline (Height h)",
                explanation = "Using third equation of motion v² = u² + 2 a s from rest (u = 0) with incline length s = h / sin θ:",
                equation = "v² = 2 · [(g sin θ) / (1 + k²/R²)] · [h / sin θ] = (2 g h) / (1 + k² / R²) \nv = √[ (2 g h) / (1 + k² / R²) ]"
            ),
            DerivationStep(
                stepNumber = 6,
                title = "Minimum Coefficient of Static Friction μ_min",
                explanation = "To prevent slipping, static friction f_s must not exceed limiting friction f_s,max = μ_s N = μ_s M g cos θ:",
                equation = "M a (k² / R²) ≤ μ_s M g cos θ \n[(M g sin θ) / (1 + k²/R²)] · (k² / R²) ≤ μ_s M g cos θ \nμ_s ≥ [tan θ · (k² / R²)] / [1 + k² / R²] = (tan θ) / [1 + R² / k²] \nμ_min = (tan θ) / [1 + R² / k²]"
            )
        ),
        finalExpression = "a = (g sin θ) / [1 + k²/R²] \nv = √[ 2gh / (1 + k²/R²) ] \nμ_min = (tan θ) / [1 + R²/k²]",
        keyAssumptions = listOf(
            "The body rolls strictly without slipping (point of contact is instantaneously at rest).",
            "Mass distribution is symmetric about the central axis of rotation.",
            "Energy dissipation due to rolling friction is neglected."
        ),
        examTips = "Remember the ranking of acceleration down incline: Solid Sphere (k²/R² = 0.4) > Solid Disc (k²/R² = 0.5) > Spherical Shell (k²/R² = 0.67) > Ring (k²/R² = 1.0). The solid sphere always reaches the bottom first!"
    ),

    // -------------------------------------------------------------
    // 14. CAPILLARY RISE (ASCENT FORMULA)
    // -------------------------------------------------------------
    Derivation(
        id = "d_capillary_rise",
        title = "Capillary Rise (Ascent Formula: h = 2T cos θ / r ρ g)",
        chapterNumber = 10,
        chapterTitle = "Mechanical Properties of Fluids",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "Derive an expression for the height 'h' to which a wetting liquid of surface tension 'T', density 'ρ', and contact angle 'θ' rises inside a narrow vertical capillary tube of internal radius 'r'.",
        diagramDescription = "Draw a container containing liquid of density ρ. Show a narrow vertical capillary tube of internal bore radius r dipped vertically. Show concave liquid meniscus with contact angle θ with the glass wall. Draw surface tension force vectors T acting along the circular contact perimeter of length 2πr inclined at angle θ with vertical. Indicate elevated liquid column of height h and radius r.",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Total Upward Force Due to Surface Tension",
                explanation = "Surface tension T acts along the line of contact between liquid meniscus and capillary wall. The perimeter of the contact circle is 2 π r. The vertical upward component of surface tension per unit length is T cos θ:",
                equation = "F_up = (T cos θ) · (2 π r) = 2 π r T cos θ"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Weight of Elevated Liquid Column",
                explanation = "The upward capillary pull balances the total downward weight of the liquid column of height h and radius r:",
                equation = "Volume of cylinder: V = π r² h \nMass of liquid: m = ρ V = π r² h ρ \nDownward weight: W = m g = π r² h ρ g"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Equating Upward Pull to Downward Weight",
                explanation = "In mechanical equilibrium, the upward surface tension force exactly equals the weight of the elevated liquid column:",
                equation = "F_up = W \n2 π r T cos θ = π r² h ρ g"
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Solving for Capillary Height h",
                explanation = "Cancel π r from both sides and solve for height h:",
                equation = "2 T cos θ = r h ρ g \nh = (2 T cos θ) / (r ρ g)"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Meniscus Volume Correction (Refined Formula)",
                explanation = "If the volume of liquid in the hemispherical meniscus above the base plane is included [V_meniscus = π r³ - ⅔ π r³ = ⅓ π r³]:",
                equation = "Total Volume = π r² [h + r/3] \nh + r/3 = (2 T cos θ) / (r ρ g)  ⟹  h = [(2 T cos θ) / (r ρ g)] - r/3 \nFor very narrow capillary tubes (r ≪ h), r/3 is negligible."
            )
        ),
        finalExpression = "h = (2 T cos θ) / (r ρ g) \n[Jurin's Law: h ∝ 1/r]",
        keyAssumptions = listOf(
            "Capillary tube bore is perfectly uniform, cylindrical, and clean.",
            "Contact angle θ is constant throughout the circular contact boundary.",
            "Liquid is wetting (θ < 90°, cos θ > 0)."
        ),
        examTips = "Mention Jurin's Law (h · r = constant): liquid rises higher in narrower tubes! If θ > 90° (as in mercury in glass), cos θ is negative, resulting in capillary depression (h < 0). Examiners look specifically for this distinction."
    ),

    // -------------------------------------------------------------
    // 15. STANDING WAVES IN ORGAN PIPES
    // -------------------------------------------------------------
    Derivation(
        id = "d_standing_waves_pipe",
        title = "Standing Waves & Harmonics in Organ Pipes (Closed & Open)",
        chapterNumber = 15,
        chapterTitle = "Waves",
        marksWeightage = "5 Marks (Board Favorite)",
        importance = "Frequent 5-Marker",
        statement = "Explain the formation of stationary longitudinal waves in: (i) an organ pipe closed at one end, and (ii) an organ pipe open at both ends. Derive expressions for their fundamental frequency and normal modes of vibration (harmonics).",
        diagramDescription = "Draw acoustic displacement standing wave modes: (i) Closed pipe of length L: Closed end is a displacement Node (N), open end is an Antinode (A). 1st mode (Fundamental: N to A, L = λ/4), 2nd mode (3rd harmonic: L = 3λ/4), 3rd mode (5th harmonic: L = 5λ/4). (ii) Open pipe of length L: Both ends are Antinodes (A) with Node(s) in between. 1st mode (Fundamental: A-N-A, L = λ/2), 2nd mode (2nd harmonic: L = λ), 3rd mode (3rd harmonic: L = 3λ/2).",
        steps = listOf(
            DerivationStep(
                stepNumber = 1,
                title = "Boundary Conditions for Air Columns",
                explanation = "When longitudinal sound waves reflect inside a pipe: (1) At a rigid closed end, air particles cannot vibrate, forming a displacement NODE. (2) At an open end, air particles are free to move, forming a displacement ANTINODE.",
                equation = "Closed end ⟹ Displacement Node (N) \nOpen end ⟹ Displacement Antinode (A)"
            ),
            DerivationStep(
                stepNumber = 2,
                title = "Case 1: Closed Organ Pipe (One End Closed)",
                explanation = "Distance between a node and consecutive antinode is λ/4. For length L:",
                equation = "Mode 1 (Fundamental / 1st Harmonic): L = λ₁ / 4  ⟹  λ₁ = 4L \n   f₁ = v / λ₁ = v / (4L) \nMode 2 (1st Overtone / 3rd Harmonic): L = 3λ₂ / 4  ⟹  λ₂ = 4L / 3 \n   f₂ = v / λ₂ = 3 [v / (4L)] = 3 f₁ \nMode 3 (2nd Overtone / 5th Harmonic): L = 5λ₃ / 4  ⟹  λ₃ = 4L / 5 \n   f₃ = v / λ₃ = 5 [v / (4L)] = 5 f₁ \nGeneral n-th mode: f_n = (2n - 1) · [v / (4L)]"
            ),
            DerivationStep(
                stepNumber = 3,
                title = "Harmonic Ratio in Closed Organ Pipe",
                explanation = "In a closed pipe, only ODD harmonics are generated:",
                equation = "f₁ : f₂ : f₃ : ... = 1 : 3 : 5 : 7 : ..."
            ),
            DerivationStep(
                stepNumber = 4,
                title = "Case 2: Open Organ Pipe (Both Ends Open)",
                explanation = "Both ends must be antinodes. Distance between two consecutive antinodes is λ/2:",
                equation = "Mode 1 (Fundamental / 1st Harmonic): L = λ₁ / 2  ⟹  λ₁ = 2L \n   f₁' = v / λ₁ = v / (2L) \nMode 2 (1st Overtone / 2nd Harmonic): L = 2 · (λ₂ / 2)  ⟹  λ₂ = L \n   f₂' = v / λ₂ = 2 [v / (2L)] = 2 f₁' \nMode 3 (2nd Overtone / 3rd Harmonic): L = 3 · (λ₃ / 2)  ⟹  λ₃ = 2L / 3 \n   f₃' = v / λ₃ = 3 [v / (2L)] = 3 f₁' \nGeneral n-th mode: f_n' = n · [v / (2L)]"
            ),
            DerivationStep(
                stepNumber = 5,
                title = "Harmonic Ratio and Musical Quality Comparison",
                explanation = "An open pipe produces ALL harmonics (even and odd):",
                equation = "f₁' : f₂' : f₃' : ... = 1 : 2 : 3 : 4 : ... \nFundamental comparison: f₁' (open) = 2 · f₁ (closed) \nBecause an open pipe produces both even and odd harmonics, its acoustic timbre is richer and more melodious than a closed pipe."
            )
        ),
        finalExpression = "Closed Pipe: f_n = (2n - 1) [v / (4L)]  (Ratio: 1 : 3 : 5 ...) \nOpen Pipe: f_n' = n [v / (2L)]  (Ratio: 1 : 2 : 3 ...)",
        keyAssumptions = listOf(
            "Air column behaves as a uniform acoustic waveguide.",
            "End corrections (e ≈ 0.6 r per open end) are neglected in the ideal case.",
            "Speed of sound v is constant throughout the pipe."
        ),
        examTips = "Highlight that an open pipe's fundamental note is an octave higher (double frequency) compared to a closed pipe of the same length! Examiners love the conclusion that open pipes produce a richer musical sound because all harmonics are present."
    )
)
