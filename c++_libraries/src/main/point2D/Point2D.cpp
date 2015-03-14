#include"Point2D.hpp"

using namespace std;

Point2D::Point2D()
{
    m_x = 0;
    m_y = 0;
    m_nom = "X";
}

Point2D::Point2D(string nom, double x, double y)
{
    m_nom = nom;
    m_x = x;
    m_y = y;
}

Point2D::Point2D(double x, double y)
{
    Point2D();
    m_x = x;
    m_y = y;
}

void Point2D::setCoordonnees(double x, double y)
{
    m_x = x;
    m_y = y;
}

Point2D Point2D::getCoordonnees()
{
    return Point2D(m_x, m_y);
}

double Point2D::getX()
{
    return m_x;
}

double Point2D::getY()
{
    return m_y;
}

double Point2D::distanceEuclidienneAvec(Point2D p2)
{
    return sqrt((p2.m_x-m_x)*(p2.m_x-m_x) + (p2.m_y-m_y)*(p2.m_y-m_y));
}

void Point2D::translaterDe(Point2D vecteur)
{
    m_x = m_x + vecteur.m_x;
    m_y = m_y + vecteur.m_y;
}

void Point2D::faireRotation(Point2D centre, double angleAParcourir)
{
    double module = distanceEuclidienneAvec(centre);
    double angleActuel = acos(m_x / module);

    m_x = centre.m_x + module * cos(angleActuel + degreToRadian(angleAParcourir));
    m_y = centre.m_y + module * sin(angleActuel + degreToRadian(angleAParcourir));
}

double Point2D::radianToDegre(double radian)
{
    return (radian*360)/(2*PI);
}

double Point2D::degreToRadian(double degre)
{
    return (degre*2*PI)/360;
}
