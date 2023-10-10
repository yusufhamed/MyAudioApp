# MyAudio: Audio App Simulator

## Overview

`MyAudio` is an audio content management simulator designed as part of my assignment for Computer Science II (CPS 209). This application facilitates the management of various audio content types, such as songs, audiobooks, and podcasts, in a simulated library. Additionally, it interacts with a virtual online store to download and manage audio content.

## Features

- **Content Management**: Organize and manage diverse audio content, including songs, audiobooks, and podcasts.
- **Online Store Interaction**: Download audio content from a simulated online store.
- **Playback Simulation**: Simulate the playback of audio content. In this app, playing a song implies displaying its lyrics.
- **Playlist Creation**: Formulate playlists using the library content. Playlists can contain mixed types of audio content.
- **Search Capabilities**: Efficient search mechanisms leveraging Java Maps for quick content lookup.
- **File I/O**: Initialize the content store from a structured text file, allowing for dynamic content changes.
- **Error Handling**: Robust exception management ensuring a smooth user experience.

## Available Commands

Here's a list of commands you can use to interact with the MyAudio app:

- **STORE**: View contents of the simulated online store.
- **DOWNLOAD**: Download specific or a range of audio content from the store.
- **DOWNLOADA**: Download all content associated with a specific artist or author.
- **DOWNLOADG**: Download songs of a particular genre.
- **SONGS**: View all songs in your library.
- **BOOKS**: View all audiobooks in your library.
- **PLAYSONG**: Simulate playback of a song (display its lyrics).
- **PLAYBOOK**: Display content from an audiobook.
- **ARTISTS**: List all artists and authors in your library.
- **DELSONG**: Remove a song from your library.
- **MAKEPL**: Create a new playlist.
- **PRINTPL**: Display playlist content.
- **ADDTOPL**: Add content to a playlist.
- **DELFROMPL**: Remove content from a playlist.
- **PLAYALLPL**: Play all content in a playlist.
- **PLAYPL**: Play a specific content from a playlist.
- **SORTBYLENGTH**, **SORTBYYEAR**, **SORTBYNAME**: Sort library by length, year, or name.
- **SEARCH**: Search the store by content title.
- **SEARCHA**: Search by artist or author.
- **SEARCHG**: Search songs by genre.


## Installation

Follow the steps below to get `MyAudio` up and running:

1. **Prerequisites**:
   - Ensure you have Java JDK installed on your system. You can check this by running:
     ```
     java -version
     ```
   - If Java is not installed, download and install it from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

2. **Clone the Repository**:
   - Clone the `MyAudio` repository to your local machine using:
     ```
     git clone https://github.com/yusufhamed/MyAudioApp.git
     ```

3. **Compile the Source Code**:
   - Navigate to the project's root directory and compile the Java files:
     ```
     cd MyAudioApp
     javac *.java
     ```

4. **Run the Application**:
   - Start the `MyAudio` simulator by running:
     ```
     java MyAudioUI
     ```

5. **Use the Commands**:
   - Once the `MyAudio` simulator is running, you can interact with it using various commands. Refer to the [Available Commands](#available-commands) section above for a list and description of all the commands you can use.

