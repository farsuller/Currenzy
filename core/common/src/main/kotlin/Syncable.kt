// Defines an interface that requires implementing classes to provide a way to sync with a Synchronizer
interface Syncable {
    // Suspend function to perform synchronization with the provided Synchronizer
    suspend fun syncWith(synchronizer: Synchronizer)
}

// A class responsible for synchronizing local data with remote data
class Synchronizer {
    // Suspend function that orchestrates the synchronization process
    suspend fun <T> start(
        // Function to fetch data from a remote source
        fetchRemoteData: suspend () -> T,
        // Function to delete existing local data
        deleteLocalData: suspend () -> Unit,
        // Function to update local data with the new data from the remote source
        updateLocalData: suspend (T) -> Unit,
    ) {
        // Fetch the remote data
        val remoteData = fetchRemoteData()
        // Delete the existing local data
        deleteLocalData()
        // Update the local data with the fetched remote data
        updateLocalData(remoteData)
    }
}