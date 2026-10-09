<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Fraud Analysis Result</title>

    <link rel="stylesheet"
          href="css/style.css">

</head>

<body>

<nav>

    <div class="logo">
        AI Fraud Detection
    </div>

    <div class="nav-links">

        <a href="index.html">
            Home
        </a>

        <a href="dashboard.html">
            Analyze Again
        </a>

    </div>

</nav>


<div class="result-container">

    <h1>
        Fraud Analysis Result
    </h1>


    <div class="result-card">

        <p>
            <strong>
                Transaction ID:
            </strong>

            ${transactionId}
        </p>


        <div class="score">

            <span>
                Risk Score
            </span>

            <strong>
                ${score}%
            </strong>

        </div>


        <p>

            <strong>
                Risk Level:
            </strong>

            ${riskLevel}

        </p>


        <p>

            <strong>
                Prediction:
            </strong>

            ${prediction}

        </p>


        <div class="reason">

            <h3>
                Detection Analysis
            </h3>

            <p>
                ${reason}
            </p>

        </div>

    </div>


    <a href="dashboard.html"
       class="button">

        Analyze Another Transaction

    </a>

</div>

</body>

</html>
