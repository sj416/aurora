function searchFlights() {
    const departure = document.getElementById('departure').value;
    const arrival = document.getElementById('arrival').value;
    const date = document.getElementById('date').value;
    // const flightcode = document.getElementById('flightcode').value;



    // 항공편 검색 결과 예시 (실제 데이터는 API 호출로 가져오면 좋음)
    const flights = [
        {
            departure: '서울',
            arrival: '도쿄',
            date: '2025-02-10',
            airline: '대한항공',
            price: '300,000원',
        },

        {
            departure: '서울',
            arrival: '뉴욕',
            date: '2025-02-15',
            airline: '유나이티드항공',
            price: '900,000원',
        },
    ];

    // 입력값에 맞는 항공편 필터링
    const filteredFlights = flights.filter(flight =>
        flight.departure === departure &&
        flight.arrival === arrival &&
        flight.date.includes(date)
    );

    // 결과 표시
    const flightResultsDiv = document.getElementById('flight-results');
    flightResultsDiv.innerHTML = '';

    if (filteredFlights.length > 0) {
        filteredFlights.forEach(flight => {
            const flightCard = document.createElement('div');
            flightCard.classList.add('flight-card');
            flightCard.innerHTML = `
                <h3>${flight.airline} (${flight.flightcode})</h3>
                <p>출발지: ${flight.departure}</p>
                <p>도착지: ${flight.arrival}</p>
                <p>날짜: ${flight.date}</p>
                <p>가격: ${flight.price}</p>
            `;
            flightResultsDiv.appendChild(flightCard);
        });
    } else {
        flightResultsDiv.innerHTML = '<p>검색된 항공편이 없습니다.</p>';
    }
}




