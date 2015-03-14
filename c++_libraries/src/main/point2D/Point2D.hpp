/*!
 * \file Point2D.cpp
 * \brief Bibliothèque de gestion de points en 2 dimensions
 * \author Christophe Cluizel
 * \date 02-04-2014
 */

#ifndef DEF_POINT2D
#define DEF_POINT2D

#include<cmath>
#include<string>

#define PI 3.1415926535

/*! \class Point2D
 *	\brief Classe représentant un point en 2D
 *	La classe gère la création et l'utilisation de points en 2D 
 */
class Point2D
{
    public :
	
	/*!
	 * \brief Constructeur de la classe Point2D
	 */
    Point2D();
    
    /*!
	 * \brief Constructeur de la classe Point2D
	 * \param nom : le nom du point 2D
	 * \param x : l'abscisse du point 2D
	 * \param y : l'ordonnee du point 2D
	 */
    Point2D(std::string nom, double x, double y);
    
    /*!
	 * \brief Constructeur de la classe Point2D
	 * \param x : l'abscisse du point 2D
	 * \param y : l'ordonnee du point 2D
	 */
    Point2D(double x, double y);

    /*!
     * \brief Fixe les coordonnées d'un point 2D
     * \param x : l'abscisse du point 2D
     * \param y : l'ordonnée du point 2D 
     */
    void setCoordonnees(double x, double y);
    
    /*!
     * \brief Récupère les coordonnées d'un point 2D
     * \return Les coordonnées du point 2D
     */
    Point2D getCoordonnees();
    
    /*!
     * \brief Récupère l'abscisse d'un point 2D
     * \return L'abscisse du point 2D
     */
    double getX();
    
    /*!
	 * \brief Récupère l'ordonnée d'un point 2D
	 * \return L'ordonnée du point 2D
	 */
    double getY();
    
    /*!
     * \brief Calcule la distance euclidienne entre 2 points 2D
     * \param p2 : le point avec lequel on souhaite calculer la distance
     * \return La distance euclidienne entre les 2 points 2D
     */
    double distanceEuclidienneAvec(Point2D p2);
    
    /*!
     *	\brief Translate un point par rapport à un vecteur
     *	\param vecteur : le vecteur de translation 
     */
    void translaterDe(Point2D vecteur);
    
    /*!
     * \brief Fait la rotation d'un point par rapport à un centre
     * \param centre : le centre de rotation
     * \param angle : l'angle de rotation avec les conventions trigonométriques. 
     * Angle positif si la rotation est dans le sens trigonométrique.
     * Angle négatif si la rotation est dans le sens horaire
     */
    void faireRotation(Point2D centre, double angle);


    private:

    double m_x;	/*!< L'abscisse du point */
    double m_y;	/*!< L'ordonnée du point */
    std::string m_nom;	/*!< Le nom du point */

    double radianToDegre(double radian);
    double degreToRadian(double degre);
};

#endif
