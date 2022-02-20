use std::fmt;
use structopt::StructOpt;

#[derive(StructOpt)]
struct Cli {
    album: Option<u64>, // TODO: This will fail if value is not an integer
}

struct PhotoData {
    album_id: u64,
    id: u64,
    title: String,
    url: String,
    thumbnail_url: String,
}

impl fmt::Display for Cli {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self.album {
            Some(album_id) => write!(f, "album: {}", album_id),
            None => write!(f, "No album specified"),
        }
    }
}

fn create_url(album: Option<u64>) -> String {
    const BASE: &str = "https://jsonplaceholder.typicode.com/photos";
    return match album {
        Some(album_id) => format!("{}?album={}", BASE, album_id),
        None => format!("{}", BASE),
    };
}

fn fetch_photos(album: Option<u64>) -> Vec<PhotoData> {
    // Ideally, we would inject `create_url` to unit test `fetch_photos` independently
    let url: String = create_url(album);
    return Vec::new();
}

#[test]
fn it_generates_correct_base_url() {
    assert_eq!(
        "https://jsonplaceholder.typicode.com/photos",
        create_url(None)
    );
}

#[test]
fn it_generates_correct_album_url() {
    assert_eq!(
        "https://jsonplaceholder.typicode.com/photos?album=123",
        create_url(Some(123))
    );
}

fn main() {
    let args = Cli::from_args();
    println!("{}", create_url(args.album));
}
