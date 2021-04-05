import Foundation
import Capacitor
import CoreServices

@objc(FileSelectPlugin)
public class FileSelectPlugin: CAPPlugin, UIDocumentPickerDelegate {
    
    var savedCall: CAPPluginCall? = nil
    
    @objc func select(_ call: CAPPluginCall) {
        savedCall = call
        
        let multiple = call.getBool("multiple") ?? true
        let extensions = call.getArray("extensions", String.self) ?? ["*"]
        var extUTIs: [String] = []
        for element in extensions
        {
            var extUTI:CFString?
            if(element == "images")
            {
                extUTI = kUTTypeImage
            }
            else if(element == "videos")
            {
                extUTI = kUTTypeVideo
            }
            else if(element == "audios")
            {
                extUTI = kUTTypeAudio
            }
            else if(element == "*")
            {
                extUTI = kUTTypeData
            }
            else
            {
                extUTI  = UTTypeCreatePreferredIdentifierForTag(
                    kUTTagClassFilenameExtension,
                    element as CFString,
                    nil
                )?.takeRetainedValue()
            }
            extUTIs.append(extUTI! as String)
        }
        
        DispatchQueue.main.async {
            let types: [String] = extUTIs
            let documentPicker = UIDocumentPickerViewController(documentTypes: types, in: .import)
            documentPicker.delegate = self
            documentPicker.modalPresentationStyle = .formSheet
            documentPicker.allowsMultipleSelection = multiple
            
            self.bridge!.viewController!.present(documentPicker, animated: true)
        }
    }
    
    public func documentPicker(_ controller: UIDocumentPickerViewController, didPickDocumentsAt urls: [URL]) {
        var files: [PluginCallResultData] = []
        for value in urls
        {
            files.append([
                "path": value.absoluteString.replacingOccurrences(of: "file:///", with: "capacitor://localhost/_capacitor_file_/"),
                "name": value.lastPathComponent,
                "extension": value.pathExtension
            ]);
        }
        savedCall!.resolve(["files": files])
    }
    
    public func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        savedCall!.reject("canceled", "1")
    }
}
