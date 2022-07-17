package org.sj.utils.math.complex;

public class Complex
{
	 public double re;
	 public double im;


	 public Complex()
	 {
		  re = 0;
		  im = 0;
	 }

	 public Complex(double a, double b)
	 {
		  re = a;
		  im = b;
	 }

	 public Complex(Complex z) {
		  re = z.re;
		  im = z.im;
	 }

	 public double module()
	 {
		  return Math.sqrt(re*re + im*im);
	 }

	 public double square_module()
	 {
		  return re*re + im*im;
	 }

	 public Complex scale(double s)
	 {
		  return new Complex(s*re, s*im);
	 }


	 public Complex getInverse()
	 {
		  double sq_m = square_module();
		  return new Complex(re / sq_m, -im / sq_m);
	 }

	 public void add(Complex z)
	 {
		  re += z.re;
		  im += z.im;
	 }

	 public void sub(Complex z)
	 {
		  re -= z.re;
		  im -= z.im;
	 }

	 public void mult(Complex z)
	 {
		  double x = re*z.re - im*z.im;
		  double y = re*z.im + im*z.re;
		  re = x;
		  im = y;
	 }

	 public void div(Complex z)
	 {
		  double m = z.module();
		  double x = re*z.re + im*z.im / m;
		  double y = re*z.im - im*z.re / m;
		  re = x;
		  im = y;
	 }

	 public  Complex getOpposite()
	 {
		  return new Complex(-re, -im);
	 }



	 public static Complex add(Complex z, Complex w)
	 {
		  return new Complex(z.re+w.re, z.im+w.im);
	 }

	 public static Complex sub(Complex z, Complex w)
	 {
		  return new Complex(z.re-w.re, z.im-w.im);
	 }


	 public static Complex mult(Complex z, Complex w)
	 {
		  return new Complex(z.re*w.re - z.im*w.im, z.re*w.im+z.im*w.re);
	 }

	 public static Complex div(Complex z, Complex w)
	 {
		  return mult(z, w.getInverse());
	 }

	 public static Complex polar(double mod, double arg) {
		  double r, i;
		  r = mod * Math.cos(arg);
		  i = mod * Math.sin(arg);
		  return new Complex(r, i);
	 }

	 public boolean isZero()
	 {
		  return (re == 0) && (im == 0);
	 }

	 public boolean is(double r, double i)
	 {
		  return (re == r) && (im == i);
	 }

	 public boolean equals(Complex z) 
	 {
		  return (re == z.re) && (im == z.im);
	 }

	 public String toString() {
		  if(im == 0) {
				return ""+re;
		  } else if(re == 0) {
				return "i"+im;
		  }
		  return "(" + re + " + i" + im + ")";
	 }
}