package com.abhijits.movieticket;

import com.abhijits.movieticket.domain.address.Address;
import com.abhijits.movieticket.domain.address.City;
import com.abhijits.movieticket.domain.enums.*;
import com.abhijits.movieticket.domain.theatre.*;
import com.abhijits.movieticket.domain.users.Account;
import com.abhijits.movieticket.domain.users.PlatformUser;
import com.abhijits.movieticket.repository.address.AddressRepository;
import com.abhijits.movieticket.repository.address.CityRepository;
import com.abhijits.movieticket.repository.booking.BookingRepository;
import com.abhijits.movieticket.repository.payment.PaymentRepository;
import com.abhijits.movieticket.repository.theatre.*;
import com.abhijits.movieticket.repository.user.AccountRepository;
import com.abhijits.movieticket.repository.user.PlatformUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
//@Component
public class DataLoader {

    private AddressRepository addressRepository;

    private CityRepository cityRepository;

    private BookingRepository bookingRepository;

    private PaymentRepository paymentRepository;

    private HallRepository hallRepository;

    private MovieRepository movieRepository;

    private SeatRepository seatRepository;

    private ShowRepository showRepository;

    private ReservationRepository reservationRepository;

    private TheatreRepository theatreRepository;

    private AccountRepository accountRepository;

    private PlatformUserRepository platformUserRepository;

    public DataLoader(
            AddressRepository addressRepository,
            CityRepository cityRepository,
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository,
            HallRepository hallRepository,
            MovieRepository movieRepository,
            SeatRepository seatRepository,
            ShowRepository showRepository,
            ReservationRepository reservationRepository,
            TheatreRepository theatreRepository,
            AccountRepository accountRepository,
            PlatformUserRepository platformUserRepository
    ) {
        this.addressRepository = addressRepository;
        this.cityRepository = cityRepository;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.hallRepository = hallRepository;
        this.movieRepository = movieRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.reservationRepository = reservationRepository;
        this.theatreRepository = theatreRepository;
        this.accountRepository = accountRepository;
        this.platformUserRepository = platformUserRepository;
    }

    @PostConstruct
    public void createData() {

        // create movies
        Movie movie1 = movieRepository.save(createMovie("Movie1", "Description 1"));
        Movie movie2 = movieRepository.save(createMovie("Movie2", "Description 2"));
        Movie movie3 = movieRepository.save(createMovie("Movie3", "Description 3"));

        // create city
        City city1 = cityRepository.save(createCity("city1", "191121"));
        City city2 = cityRepository.save(createCity("city2", "191122"));
        City city3 = cityRepository.save(createCity("city3", "191123"));

        System.out.println("Movies :");
        System.out.println(movie1);
        System.out.println(movie2);
        System.out.println(movie3);

        System.out.println("Cities :");
        System.out.println(city1);
        System.out.println(city2);
        System.out.println(city3);

        // create Platform user
        PlatformUser user = platformUserRepository.save(createPlatformUser(
                addressRepository.save(createAddress("Secotr - 62", city1)),
                accountRepository.save(createAccount())
        ));

        createTheaters(city1, 3, Arrays.asList(movie1, movie2, movie3));
        createTheaters(city2, 3, Arrays.asList(movie2, movie3));
        createTheaters(city3, 3, Arrays.asList(movie1, movie2));

    }

    private List<Theatre> createTheaters(City city, int noOfTheaters, List<Movie> movies) {
        List<Theatre> theatres = new ArrayList<>();

        for (int i=0; i<noOfTheaters; i++) {
            theatres.add(createTheatre(city.getCity() + " Theatre" + i, "Sector " + i, city, 3, movies));
        }

        return theatres;
    }

    private Theatre createTheatre(String name, String address, City city, int noOfShowsPerHall, List<Movie> movies) {
        Theatre theatre = theatreRepository.save(createTheatre(
                name,
                addressRepository.save(createAddress(address, city))
        ));

        int noOfHalls = movies.size();

        for (int i=0; i< noOfHalls; i++) {
            Hall hall = hallRepository.save(createHall("Hall " + i, theatre));
            theatre.addHall(hall);

            for (int k=0; k<10; k++) {
                for (int j=0; j<10; j++) {
                    Seat seat = seatRepository.save(createSeat(hall, i, j, SeatType.REGULAR));
                    hall.addSeat(seat);
                }
            }
            hall.setTotalSeats(hall.getSeats().size());

            for (int k=0; k<noOfShowsPerHall; k++) {
                Movie movie = movies.get(i);
                Show show = showRepository.save(createShow(movie, hall, ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(10+(k*3))));
                hall.addShow(show);
                movie.addShow(show);
            }

//            hall.getShows().forEach(show -> {
//                hall.getSeats().forEach(seat -> {
//                    showSeatRepository.save(createShowSeat(seat, show));
//                });
//            });
        }

        return theatre;
    }

    private PlatformUser createPlatformUser(Address address, Account account) {
        return new PlatformUser()
                .setFirstName("Vikas")
                .setLastName("Goel")
                .setEmail("vikas.goel@test.com")
                .setPhoneNo("9999999999")
                .setType(UserType.CUSTOMER)
                .setAddress(address)
                .setAccount(account);

        // Add Account, Address and Bookings

    }

    private Reservation createShowSeat(Seat seat, Show show) {
        return new Reservation()
                .setShow(show)
                .setSeat(seat)
                .setPrice(30.0)
                .setStatus(ReservationStatus.AVAILABLE);
    }

    private Show createShow(Movie movie, Hall hall, ZonedDateTime date) {
        Show show = new Show()
                .setMovie(movie)
                .setHall(hall)
                .setStartTime(date.plusHours(3))
                .setEndTime(date.plusHours(5));



        return show;
    }

    private Seat createSeat(Hall hall, int column, int row, SeatType type) {
        Seat seat = new Seat()
                .setColumnNo(column)
                .setRowNo(row)
                .setType(type)
                .setHall(hall);
        return seat;
    }

    private Hall createHall(String hallName, Theatre theatre) {
        Hall hall = new Hall()
                .setName(hallName)
                .setTheatre(theatre);

        // add Seat, add Show, total number of seats
        return hall;
    }

    private Theatre createTheatre(String theatreName, Address address) {
        return new Theatre()
                .setName(theatreName)
                .setAddress(address);

        // Add Hall
    }

    private Account createAccount() {
        return new Account()
                .setUserId("user1")
                .setPassword("pass")
                .setStatus(AccountStatus.ACTIVE);
    }

    private Movie createMovie(String movieName, String movieDescription) {
        return new Movie()
                .setTitle(movieName)
                .setDescription(movieDescription)
                .setGenre(Genre.ACTION)
                .setLanguage("English")
                .setDurationInMins(120)
                .setReleaseDate(ZonedDateTime.now());

        // Add Shows;
    }

    private Address createAddress(String addressLine1, City city) {
        return new Address()
                .setAddressLine1(addressLine1)
                .setCity(city);
    }

    private City createCity(String cityName, String zipCode) {
        return new City()
                .setCity(cityName)
                .setState("Uttar Pradesh")
                .setCountry("India")
                .setZipCode(zipCode);
    }

}
