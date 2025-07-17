<%@include file="layout/header.jsp"%>
  

<div class="content">
  <div class="container-fluid">
    <h1 class="mb-4">Bienvenue dans le tableau de bord</h1>

    <div class="row">
      <div class="col-md-6 mb-3">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Stations</h5>
            <p class="card-text">Voir la liste des stations enregistr�es.</p>
            <a href="stations/index.jsp" class="btn btn-primary">G�rer</a>
          </div>
        </div>
      </div>

      <div class="col-md-6 mb-3">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Ventes</h5>
            <p class="card-text">Historique des ventes journali�res.</p>
            <a href="ventes/index.jsp" class="btn btn-primary">Consulter</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="/layout/footer.jsp" %>
