<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</main><!-- Fin du main-content -->
</div><!-- Fin du main-wrapper -->

<!-- Footer -->
<footer class="main-footer">
    <div class="footer-container">
        <div class="footer-content">
            <div class="footer-section">
                <div class="footer-logo">
                    <i class="fas fa-building"></i>
                    <span>GestionLocation</span>
                </div>
                <p class="footer-description">
                    Solution complète de gestion immobilière pour propriétaires et locataires.
                </p>
                <div class="social-links">
                    <a href="#" class="social-link"><i class="fab fa-facebook"></i></a>
                    <a href="#" class="social-link"><i class="fab fa-twitter"></i></a>
                    <a href="#" class="social-link"><i class="fab fa-linkedin"></i></a>
                    <a href="#" class="social-link"><i class="fab fa-instagram"></i></a>
                </div>
            </div>

            <div class="footer-section">
                <h4>Liens Rapides</h4>
                <ul class="footer-links">
                    <c:choose>
                        <c:when test="${sessionScope.userRole == 'ADMIN'}">
                            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/utilisateurs">Utilisateurs</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/rapports">Rapports</a></li>
                        </c:when>
                        <c:when test="${sessionScope.userRole == 'PROPRIETAIRE'}">
                            <li><a href="${pageContext.request.contextPath}/proprietaire/dashboard">Dashboard</a></li>
                            <li><a href="${pageContext.request.contextPath}/proprietaire/immeubles">Immeubles</a></li>
                            <li><a href="${pageContext.request.contextPath}/proprietaire/unites">Unités</a></li>
                            <li><a href="${pageContext.request.contextPath}/proprietaire/contrats">Contrats</a></li>
                        </c:when>
                        <c:when test="${sessionScope.userRole == 'LOCATAIRE'}">
                            <li><a href="${pageContext.request.contextPath}/locataire/dashboard">Dashboard</a></li>
                            <li><a href="${pageContext.request.contextPath}/locataire/offres">Offres</a></li>
                            <li><a href="${pageContext.request.contextPath}/locataire/paiements">Paiements</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/">Accueil</a></li>
                            <li><a href="${pageContext.request.contextPath}/login">Connexion</a></li>
                            <li><a href="${pageContext.request.contextPath}/register">Inscription</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>

            <div class="footer-section">
                <h4>Support</h4>
                <ul class="footer-links">
                    <li><a href="${pageContext.request.contextPath}/help">Centre d'aide</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                    <li><a href="${pageContext.request.contextPath}/faq">FAQ</a></li>
                    <li><a href="${pageContext.request.contextPath}/privacy">Politique de confidentialité</a></li>
                    <li><a href="${pageContext.request.contextPath}/terms">Conditions d'utilisation</a></li>
                </ul>
            </div>

            <div class="footer-section">
                <h4>Contact</h4>
                <div class="contact-info">
                    <div class="contact-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <span>Jaxaay, Keur Massar, Dakar, Senegal </span>
                    </div>
                    <div class="contact-item">
                        <i class="fas fa-phone"></i>
                        <span>+221 70 767 27 92</span>
                    </div>
                    <div class="contact-item">
                        <i class="fas fa-envelope"></i>
                        <span>jeanphilippebianquinch@gmail.com</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="footer-bottom">
            <div class="footer-bottom-content">
                <p>&copy; 2025 GestionLocation. Tous droits réservés.</p>
                <div class="footer-bottom-links">
                    <span>Développé avec Mouha Kane </span>
                    <i class="fas fa-heart" style="color: #e74c3c;"></i>
                    <span>par TeamProj</span>
                </div>
            </div>
        </div>
    </div>
</footer>

<!-- JavaScript -->
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

<!-- Scripts spécifiques à la page -->
<c:if test="${not empty param.scripts}">
    <c:forTokens items="${param.scripts}" delims="," var="script">
        <script src="${pageContext.request.contextPath}/assets/js/${script}.js"></script>
    </c:forTokens>
</c:if>

<!-- Messages toast -->
<div class="toast-container" id="toastContainer"></div>

<!-- Loading overlay -->
<div class="loading-overlay" id="loadingOverlay">
    <div class="loading-spinner">
        <i class="fas fa-spinner fa-spin"></i>
        <span>Chargement...</span>
    </div>
</div>

<!-- Modal container -->
<div class="modal-container" id="modalContainer"></div>
</body>
</html> 