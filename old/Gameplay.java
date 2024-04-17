package fr.cubibox.sandbox.old;

public class Gameplay {
    /*
    @FXML
    private Pane pane;

    // Variables nécessaire pour la boucle
    private long targetFps = 60L;
    private long lastTime = 0L;
    //

    public static float screenDistance = 120F;
    private float wallHeight = 16F;
    public static int rayCount = 720;


    // Variables pour charger les modèles / textures / objects à charger sur la map.
    private int[][] wallTextureMatrix;
    private int[][] floorTextureMatrix;
    private int[][] ceilTextureMatrix;
    //

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // es
        System.out.println("Chargement des textures..");

        // Chargement des textures de mur
        wallTextureMatrix = new int[TILE_SIZE][TILE_SIZE];
        BufferedImage wallTexture = ImageUtils.readImage("textures/wall.png");

        // Chargement des textures de sol
        ceilTextureMatrix = new int[TILE_SIZE][TILE_SIZE];
        BufferedImage ceilTexture = ImageUtils.readImage("textures/ceil.png");

        // Chargement des textures de plafond
        floorTextureMatrix = new int[TILE_SIZE][TILE_SIZE];
        BufferedImage floorTexture = ImageUtils.readImage("textures/floor.png");

        for (int y = 0; y < (TILE_SIZE); y++) {
            for (int x = 0; x < (TILE_SIZE); x++) {
                wallTextureMatrix[y][x] = wallTexture.getRGB(x, y);
                ceilTextureMatrix[y][x] = ceilTexture.getRGB(x, y);
                floorTextureMatrix[y][x] = floorTexture.getRGB(x, y);
            }
        }

        System.out.println("Textures chargées !");

        this.start();
    }

    @Override
    public void handle(long currentTime) {
        long interval = ONE_SECOND_IN_NANO / targetFps;

        if (currentTime - lastTime > interval) {
            pane.getChildren().clear();

            Canvas canvas = new Canvas((int) pane.getWidth(), (int) pane.getHeight());
            update(canvas);

            PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(canvas.width, canvas.height, canvas.getBuffer(), PixelFormat.getIntArgbPreInstance());
            WritableImage image = new WritableImage(pixelBuffer);
            pixelBuffer.updateBuffer(b -> null);
            pane.getChildren().add(new ImageView(image));

            lastTime = currentTime;
        }
    }

    private void update(Canvas canvas) {
        int rayCount = 720;

        Player player = null;//getEngine().getPlayer();


        int angleStep = windowWidth / rayCount;
        float halfWindowWidth = windowWidth / 2f;
        int halfHeight = windowHeight / 2;

        for (int x = 0; x <= rayCount; x++) {
            float step = x * angleStep;
            float rayAngle = ((float) Math.atan((step - halfWindowWidth) / halfWindowWidth) / RADIAN_PI_2) + player.getAngle();

            Ray r = new Ray(player.getX(), player.getY(), rayAngle);
            updateRay(r);

            for (float y = 1; y < halfHeight; y++) {
                float directDistFloor = (screenDistance * halfHeight) / (int) (y);
                float realDistFloor = (float) (directDistFloor / Math.cos((player.getAngle() - r.getAngle()) * RADIAN_PI_2));

                float floorX = (float) (player.getX() + Math.cos(r.getAngle() * RADIAN_PI_2) * realDistFloor / (screenDistance / 2f));
                float floorY = (float) (player.getY() + Math.sin(r.getAngle() * RADIAN_PI_2) * realDistFloor / (screenDistance / 2f));

                r.addFloorHit(new Point2F(floorX, floorY));
            }

            drawRay(r, x, canvas);
        }
    }

    private void drawRay(Ray ray, int rayIndex, Canvas canvas) {
        float width = windowWidth;
        float height = windowHeight;
        float halfHeight = height / 2f;

        if (ray != null) {
            float rayDX = ray.getIntersectionX() - ray.getStartX();
            float rayDY = ray.getIntersectionY() - ray.getStartY();

            float rayDistance = (float) (Math.pow((rayDX * rayDX) + (rayDY * rayDY), 0.5));

            // Perspective correction
            float rayAngleFromMiddle = 0f;//getEngine().getPlayer().getAngle() - ray.getAngle();
            float rayAngleDiffAbsCos = (float) abs(Math.cos(rayAngleFromMiddle * RADIAN_PI_2));
            rayDistance = rayDistance * rayAngleDiffAbsCos;

            float perceivedHeight = (screenDistance * (wallHeight / rayDistance)) / 6f;

            // Draw Rectangle
            float startX = rayIndex;
            float startY = (height - perceivedHeight) / 2f;

            float wallToBorder = (canvas.height - perceivedHeight) / 2f;

            // Draw floor and ceil
            if (wallToBorder > 0) {
                float y = 1;

                for (Point2F p : ray.getFloorPoints()) {
                    int floorTextureX = (int) ((p.getX() - Math.floor(p.getX())) * TILE_SIZE) % TILE_SIZE;
                    int floorTextureY = (int) ((p.getY() - Math.floor(p.getY())) * TILE_SIZE) % TILE_SIZE;

                    canvas.drawPixel(
                            new Point2F(startX, halfHeight - y),
                            floorTextureMatrix[floorTextureY][floorTextureX]
                    );

                    canvas.drawPixel(
                            new Point2F(startX, halfHeight + y - 1),
                            ceilTextureMatrix[floorTextureY][floorTextureX]
                    );

                    y++;
                }
            }

            canvas.fillRect(
                    new Rectangle2F(startX, startY, 1, perceivedHeight),
                    new Color(100, 100, 100).getRGB()
            );
        }
    }

    private ArrayList<Chunk> findTraveledChunk() {
        ArrayList<Chunk> chunksFound = new ArrayList<>();
        Chunk[][] chunks = null;//getEngine().getMap().getChunks();

        for (Chunk[] LineChunk : chunks)
            Collections.addAll(chunksFound, LineChunk);

        return chunksFound;
    }

    private void getIntersectEdge(Ray r, ArrayList<Chunk> chunks) {
        float dRay = Float.MAX_VALUE;
        float tempX = r.getIntersectionX();
        float tempY = r.getIntersectionY();

        int textureID = 0;

        for (Chunk chunk : chunks) {
            if (chunk != null) {
                for (MapObject mapObject : chunk.getMapObjects()) {
                    for (Line2F edge : mapObject.getEdges()) {
                        float p1X = r.getStartX();
                        float p1Y = r.getStartY();
                        float p2X = r.getIntersectionX();
                        float p2Y = r.getIntersectionY();

                        float p3X = edge.getA().getX();
                        float p3Y = edge.getA().getY();
                        float p4X = edge.getB().getX();
                        float p4Y = edge.getB().getY();

                        float s1_x, s1_y, s2_x, s2_y;
                        s1_x = p2X - p1X;
                        s1_y = p2Y - p1Y;
                        s2_x = p4X - p3X;
                        s2_y = p4Y - p3Y;

                        float s, t;
                        float v = -s2_x * s1_y + s1_x * s2_y;
                        s = (-s1_y * (p1X - p3X) + s1_x * (p1Y - p3Y)) / v;
                        t = (s2_x * (p1Y - p3Y) - s2_y * (p1X - p3X)) / v;

                        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
                            float intx = p1X + (t * s1_x);
                            float inty = p1Y + (t * s1_y);

                            float tempdRay = computeSquareRayDistance(r, intx, inty);
                            if (tempdRay < dRay) {
                                dRay = tempdRay;
                                tempX = intx;
                                tempY = inty;

                                float dx = tempX - p3X;
                                float dy = tempY - p3Y;

                                textureID = (int) ((abs(dx) + abs(dy)) * (TILE_SIZE / 2)) % TILE_SIZE;
                            }
                        }
                    }
                }
            }
        }

        r.setTextureIndex(textureID);
        r.setIntersectionX(tempX);
        r.setIntersectionY(tempY);
    }

    /**
     * actualise la distance entre la line et le joueur du Rayon (DRay)
     * actualise egalement le intersectionPoint du Rayon

    private float computeSquareRayDistance(Ray r, float x, float y) {
        float x1 = r.getStartX();
        float y1 = r.getStartY();

        float dx = x1 - x;
        float dy = y1 - y;

        return dx * dx + dy * dy;
    }

    private void updateRay(Ray r) {
        ArrayList<Chunk> chunks = findTraveledChunk();
        getIntersectEdge(r, chunks);
    }
    */
}
