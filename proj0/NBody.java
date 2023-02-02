public class NBody {
    public static double readRadius(String file){
        In in = new In(file);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int N = in.readInt();
        in.readDouble();
        Planet[] ps = new Planet[N];
        for (int i = 0; i < N; i += 1){
            double xP = in.readDouble(),
                   yP = in.readDouble(),
                   xV = in.readDouble(),
                   yV = in.readDouble(),
                   m = in.readDouble();
            String img = in.readString();
            ps[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return ps;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]), dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double r = readRadius(filename);
        Planet[] ps = readPlanets(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-r, r);

        for (int t = 0; t < T; t += dt){
            double[] xForces = new double[ps.length],
                     yForces = new double[ps.length];
            for (int i = 0; i < ps.length; i += 1){
                xForces[i] = ps[i].calcNetForceExertedByX(ps);
                yForces[i] = ps[i].calcNetForceExertedByY(ps);
            }
            for (int i = 0; i < ps.length; i += 1) {
                ps[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : ps){
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", ps.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < ps.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    ps[i].xxPos, ps[i].yyPos, ps[i].xxVel,
                    ps[i].yyVel, ps[i].mass, ps[i].imgFileName);
        }
    }
}
