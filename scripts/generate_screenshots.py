import os
import shutil
from PIL import Image, ImageDraw, ImageFont, ImageFilter

def create_gradient(width, height, top_color, bottom_color):
    """Creates a smooth vertical gradient."""
    top_r, top_g, top_b = top_color
    bot_r, bot_g, bot_b = bottom_color
    
    grad = Image.new('RGB', (width, height))
    draw = ImageDraw.Draw(grad)
    for y in range(height):
        factor = y / float(height - 1)
        r = int(top_r + (bot_r - top_r) * factor)
        g = int(top_g + (bot_g - top_g) * factor)
        b = int(top_b + (bot_b - top_b) * factor)
        draw.line([(0, y), (width, y)], fill=(r, g, b))
    return grad

def add_radial_glow(image, center_x, center_y, radius, color, alpha=70):
    """Adds a soft atmospheric radial glow for modern depth."""
    overlay = Image.new('RGBA', image.size, (0, 0, 0, 0))
    glow_draw = ImageDraw.Draw(overlay)
    
    steps = 45
    r, g, b = color
    for i in range(steps, 0, -1):
        cur_radius = int(radius * (i / steps))
        cur_alpha = int(alpha * (1.0 - (i / steps)) ** 1.8)
        bbox = [
            center_x - cur_radius,
            center_y - cur_radius,
            center_x + cur_radius,
            center_y + cur_radius
        ]
        glow_draw.ellipse(bbox, fill=(r, g, b, cur_alpha))
    
    overlay = overlay.filter(ImageFilter.GaussianBlur(20))
    return Image.alpha_composite(image.convert('RGBA'), overlay)

def create_rounded_mask(size, radius):
    """Creates an antialiased rounded rectangle mask."""
    w, h = size
    mask_large = Image.new('L', (w * 2, h * 2), 0)
    draw = ImageDraw.Draw(mask_large)
    draw.rounded_rectangle([0, 0, w * 2, h * 2], radius=radius * 2, fill=255)
    return mask_large.resize(size, Image.Resampling.LANCZOS)

def render_screenshot(
    raw_img_path,
    output_path,
    tag_text,
    title_line1,
    title_line2,
    subtitle_text,
    theme_colors
):
    CANVAS_W = 1080
    CANVAS_H = 2280
    
    top_color = theme_colors['top_bg']
    bottom_color = theme_colors['bot_bg']
    accent_color = theme_colors['accent']
    glow_color = theme_colors.get('glow', accent_color)
    
    # 1. Background gradient & atmospheric ambient lighting
    canvas = create_gradient(CANVAS_W, CANVAS_H, top_color, bottom_color)
    canvas = add_radial_glow(canvas, CANVAS_W // 2, 680, 580, glow_color, alpha=75)
    canvas = canvas.convert('RGBA')
    
    draw = ImageDraw.Draw(canvas)
    
    # Typography
    font_bold_path = "C:/Windows/Fonts/segoeuib.ttf"
    font_reg_path = "C:/Windows/Fonts/segoeui.ttf"
    
    tag_font = ImageFont.truetype(font_bold_path, 25)
    title_font = ImageFont.truetype(font_bold_path, 60)
    sub_font = ImageFont.truetype(font_reg_path, 32)
    
    # 2. Glassmorphism Pill Badge Tag
    tag_bbox = draw.textbbox((0, 0), tag_text, font=tag_font)
    tag_w = tag_bbox[2] - tag_bbox[0]
    tag_h = tag_bbox[3] - tag_bbox[1]
    
    pill_pad_x = 28
    pill_pad_y = 10
    pill_w = tag_w + (pill_pad_x * 2)
    pill_h = tag_h + (pill_pad_y * 2)
    
    pill_x0 = (CANVAS_W - pill_w) // 2
    pill_y0 = 85
    pill_x1 = pill_x0 + pill_w
    pill_y1 = pill_y0 + pill_h
    
    pill_layer = Image.new('RGBA', canvas.size, (0, 0, 0, 0))
    p_draw = ImageDraw.Draw(pill_layer)
    ar, ag, ab = accent_color
    p_draw.rounded_rectangle(
        [pill_x0, pill_y0, pill_x1, pill_y1],
        radius=pill_h // 2,
        fill=(ar, ag, ab, 45),
        outline=(ar, ag, ab, 220),
        width=2
    )
    p_draw.text(
        (pill_x0 + pill_pad_x, pill_y0 + pill_pad_y - 2),
        tag_text,
        font=tag_font,
        fill=(255, 255, 255, 255)
    )
    canvas = Image.alpha_composite(canvas, pill_layer)
    draw = ImageDraw.Draw(canvas)
    
    # 3. Main Title
    current_y = pill_y1 + 28
    if title_line1:
        t1_bbox = draw.textbbox((0, 0), title_line1, font=title_font)
        t1_w = t1_bbox[2] - t1_bbox[0]
        draw.text(((CANVAS_W - t1_w) // 2, current_y), title_line1, font=title_font, fill=(255, 255, 255, 255))
        current_y += (t1_bbox[3] - t1_bbox[1]) + 12
        
    if title_line2:
        t2_bbox = draw.textbbox((0, 0), title_line2, font=title_font)
        t2_w = t2_bbox[2] - t2_bbox[0]
        draw.text(((CANVAS_W - t2_w) // 2, current_y), title_line2, font=title_font, fill=(255, 255, 255, 255))
        current_y += (t2_bbox[3] - t2_bbox[1]) + 16
    else:
        current_y += 8

    # 4. Subtitle
    sub_bbox = draw.textbbox((0, 0), subtitle_text, font=sub_font)
    sub_w = sub_bbox[2] - sub_bbox[0]
    draw.text(((CANVAS_W - sub_w) // 2, current_y), subtitle_text, font=sub_font, fill=(203, 213, 225, 235))
    
    # 5. Device Mockup Frame
    PHONE_W = 890
    PHONE_H = 1750
    PHONE_X = (CANVAS_W - PHONE_W) // 2
    PHONE_Y = 490
    CORNER_R = 52
    BEZEL = 14
    
    # Multi-layer realistic drop shadow
    shadow = Image.new('RGBA', (CANVAS_W, CANVAS_H), (0, 0, 0, 0))
    s_draw = ImageDraw.Draw(shadow)
    s_draw.rounded_rectangle(
        [PHONE_X - 10, PHONE_Y + 16, PHONE_X + PHONE_W + 10, PHONE_Y + PHONE_H + 16],
        radius=CORNER_R + 6,
        fill=(0, 0, 0, 160)
    )
    shadow = shadow.filter(ImageFilter.GaussianBlur(32))
    canvas = Image.alpha_composite(canvas, shadow)
    draw = ImageDraw.Draw(canvas)
    
    # Outer device chassis (sleek dark titanium)
    draw.rounded_rectangle(
        [PHONE_X, PHONE_Y, PHONE_X + PHONE_W, PHONE_Y + PHONE_H],
        radius=CORNER_R,
        fill=(22, 22, 26, 255),
        outline=(55, 55, 65, 255),
        width=3
    )
    # Subtle inner highlight rim matching the screenshot's accent glow
    draw.rounded_rectangle(
        [PHONE_X + 2, PHONE_Y + 2, PHONE_X + PHONE_W - 2, PHONE_Y + PHONE_H - 2],
        radius=CORNER_R - 2,
        outline=(ar, ag, ab, 50),
        width=1
    )
    
    # Inner screen
    SCREEN_X = PHONE_X + BEZEL
    SCREEN_Y = PHONE_Y + BEZEL
    SCREEN_W = PHONE_W - (BEZEL * 2)
    SCREEN_H = PHONE_H - (BEZEL * 2)
    SCREEN_CORNER_R = CORNER_R - BEZEL
    
    # Load and prepare raw screenshot
    raw_img = Image.open(raw_img_path).convert('RGB')
    
    # Resize screenshot preserving aspect ratio
    raw_w, raw_h = raw_img.size
    aspect = SCREEN_W / float(raw_w)
    new_h = int(raw_h * aspect)
    resized_raw = raw_img.resize((SCREEN_W, new_h), Image.Resampling.LANCZOS)
    
    if new_h < SCREEN_H:
        # Extend bottom with background color of screenshot bottom
        screen_content = Image.new('RGB', (SCREEN_W, SCREEN_H), resized_raw.getpixel((SCREEN_W // 2, new_h - 1)))
        screen_content.paste(resized_raw, (0, 0))
    else:
        # Crop from top
        screen_content = resized_raw.crop((0, 0, SCREEN_W, SCREEN_H))
        
    screen_mask = create_rounded_mask((SCREEN_W, SCREEN_H), SCREEN_CORNER_R)
    
    # Paste onto canvas
    canvas.paste(screen_content, (SCREEN_X, SCREEN_Y), screen_mask)
    
    # Save final image
    canvas.convert('RGB').save(output_path, 'PNG', quality=95)
    print(f"Generated: {output_path}")

def generate_all():
    raw_dir = 'app/src/main/play/listings/en-US/graphics/raw-screenshots'
    out_dir = 'app/src/main/play/listings/en-US/graphics/phone-screenshots'
    os.makedirs(out_dir, exist_ok=True)
    
    screens = [
        {
            'raw': os.path.join(raw_dir, '1.png'),
            'out': os.path.join(out_dir, '1.png'),
            'tag': 'COMPLETE NCERT SYLLABUS',
            'title1': 'Class 11 Physics Notes',
            'title2': 'All 15 Chapters Included',
            'sub': 'Simplified Theory, Key Formulas & Derivations',
            'theme': {
                'top_bg': (10, 20, 48),       # Deep Sapphire
                'bot_bg': (2, 6, 23),         # Midnight Slate
                'accent': (56, 189, 248),     # Electric Sky Blue
                'glow': (14, 165, 233)
            }
        },
        {
            'raw': os.path.join(raw_dir, '4.png'),
            'out': os.path.join(out_dir, '2.png'),
            'tag': 'INTERACTIVE 3D SIMULATION',
            'title1': 'Virtual 3D Physics Labs',
            'title2': "Simulate Newton's Laws Live",
            'sub': 'Adjust Mass, Force & Friction with Real-Time 3D',
            'theme': {
                'top_bg': (28, 14, 56),       # Deep Cyber Purple
                'bot_bg': (10, 4, 24),
                'accent': (192, 132, 252),    # Neon Violet
                'glow': (168, 85, 247)
            }
        },
        {
            'raw': os.path.join(raw_dir, '3.png'),
            'out': os.path.join(out_dir, '3.png'),
            'tag': 'SMART ACTIVE REVISION',
            'title1': 'Concept Flashcards',
            'title2': 'Quick Memory Boost',
            'sub': 'Flip & Master Formulas, Units & Definitions',
            'theme': {
                'top_bg': (8, 38, 34),        # Deep Emerald
                'bot_bg': (2, 20, 18),
                'accent': (52, 211, 153),     # Emerald Mint
                'glow': (16, 185, 129)
            }
        },
        {
            'raw': os.path.join(raw_dir, '2.png'),
            'out': os.path.join(out_dir, '4.png'),
            'tag': 'EXAM PRACTICE MCQs',
            'title1': 'Chapter-Wise Quizzes',
            'title2': 'For CBSE, NEET & JEE',
            'sub': 'Instant Scoring, Streaks & Solution Review',
            'theme': {
                'top_bg': (42, 12, 36),       # Deep Vivid Magenta
                'bot_bg': (18, 4, 16),
                'accent': (244, 114, 182),    # Vibrant Pink
                'glow': (236, 72, 153)
            }
        },
        {
            'raw': os.path.join(raw_dir, '6.png'),
            'out': os.path.join(out_dir, '5.png'),
            'tag': '3D WAVE SIMULATOR',
            'title1': 'Wave Mechanics in 3D',
            'title2': 'Interactive Experiments',
            'sub': 'Control Frequency, Amplitude & Speed Live',
            'theme': {
                'top_bg': (8, 34, 58),        # Deep Oceanic Cyan
                'bot_bg': (2, 14, 28),
                'accent': (34, 211, 238),     # Electric Cyan
                'glow': (6, 182, 212)
            }
        },
        {
            'raw': os.path.join(raw_dir, '7.png'),
            'out': os.path.join(out_dir, '6.png'),
            'tag': 'KINETIC THEORY LAB',
            'title1': '3D Gas Molecule Lab',
            'title2': 'Real-Time Thermodynamics',
            'sub': 'Simulate Gas Laws, Temperature & Pressure',
            'theme': {
                'top_bg': (42, 30, 8),        # Solar Amber / Bronze
                'bot_bg': (18, 12, 2),
                'accent': (251, 191, 36),     # Golden Amber
                'glow': (245, 158, 11)
            }
        },
        {
            'raw': os.path.join(raw_dir, '5.png'),
            'out': os.path.join(out_dir, '7.png'),
            'tag': 'GAMIFIED LEARNING',
            'title1': 'Concept Match Game',
            'title2': 'Master Terms & Laws',
            'sub': 'Fun Memory Matching for High Retention',
            'theme': {
                'top_bg': (24, 16, 52),       # Deep Electric Indigo
                'bot_bg': (8, 5, 22),
                'accent': (129, 140, 248),    # Indigo
                'glow': (99, 102, 241)
            }
        }
    ]
    
    for s in screens:
        render_screenshot(
            s['raw'],
            s['out'],
            s['tag'],
            s['title1'],
            s['title2'],
            s['sub'],
            s['theme']
        )
    print("All 7 screenshots generated successfully!")

if __name__ == '__main__':
    generate_all()
