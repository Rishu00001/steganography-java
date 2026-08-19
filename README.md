graph TD
    %% Styling
    classDef interface fill:#f9f,stroke:#333,stroke-width:2px;
    classDef abstract fill:#bbf,stroke:#333,stroke-width:2px;
    classDef logic fill:#d4edda,stroke:#28a745,stroke-width:2px;
    classDef io fill:#f8d7da,stroke:#dc3545,stroke-width:2px;

    %% Components
    Main[Main.java<br>Entry Point & UI] -->|Creates Thread| Thread(ProcessTask.java<br>Runnable Thread)
    
    Thread -->|Saves Logs| History[(SessionHistory.java<br>Collections)]
    Thread -->|Calls| Interface{SteganoAlgorithm.java<br>Interface}:::interface
    
    Logic[LSBEncoder.java<br>Core Bitwise Engine]:::logic -.->|Implements| Interface
    Logic -.->|Extends| Base[BaseImageProcessor.java<br>Abstract File I/O]:::abstract
    
    Logic -->|Throws on Error| Exception[ImageTooSmallException.java]
    
    %% Inputs and Outputs
    Cover[Cover Image .png]:::io --> Logic
    Msg[Secret Message Text]:::io --> Logic
    Logic --> Output[Stego-Image Output .png]:::io
